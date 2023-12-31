/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package inference.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import com.example.HelloServiceGrpc;
import com.example.GrpcTest;

import ai.onnxruntime.NodeInfo;
import ai.onnxruntime.TensorInfo;
import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtSession.Result;
import ai.onnxruntime.OrtSession.SessionOptions;
import ai.onnxruntime.OrtSession.SessionOptions.OptLevel;
import ai.onnxruntime.OrtProvider;


import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;


import java.util.Collections;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class AppGrpcTest {

    @Test 
    public void GPURunTest() throws OrtException, IOException {
        OrtEnvironment env = OrtEnvironment.getEnvironment();

        OrtSession.SessionOptions opts = new SessionOptions();
        opts.setOptimizationLevel(OptLevel.BASIC_OPT);
        opts.addCUDA(0);

        OrtSession pproc_sess;
        OrtSession model_sess;
        String resources_path = System.getenv("RESOURCES_PATH");

        resources_path = resources_path != null ? resources_path : "src/main/resources";

        pproc_sess = env.createSession(resources_path + "/simple_image_preprocessor.onnx", opts);
        model_sess = env.createSession(resources_path + "/mobilenetv2.onnx", opts);


        TensorInfo inputTensorInfo = (TensorInfo) model_sess.getInputInfo().get("input").getInfo();
        long[] shape = inputTensorInfo.getShape();

        String inputName = model_sess.getInputNames().iterator().next();

        // Input data
        int batch = 1;
        long channel = shape[1];
        long row = shape[2];
        long col = shape[3];

        BufferedImage image = readImage(resources_path + "/tokkis.jpg");
        String base64 = imageToBase64(image);
        BufferedImage bimg = base64ToImage(base64);
        float[] fimg = bufferTofloatImage(bimg);

        long[] orgShape = { 1, 3, 1694, 2880 };
        long[] inputShape = { 1, channel, row, col };

        OnnxTensor dataTensor = OnnxTensor.createTensor(env, fimg);
        OnnxTensor orgShapeTensor = OnnxTensor.createTensor(env, (Object) orgShape);
        OnnxTensor inputShapeTensor = OnnxTensor.createTensor(env, (Object) inputShape);

        Map<String, OnnxTensor> inputArgs = new HashMap();
        inputArgs.put("RawImg", dataTensor);
        inputArgs.put("shape", orgShapeTensor);
        inputArgs.put("sizes", inputShapeTensor);

        Result pproc_result = pproc_sess.run(inputArgs);
        float[][][][] pproc_img = (float[][][][]) pproc_result.get(0).getValue();

        OnnxTensor inputTensor = OnnxTensor.createTensor(env, pproc_img);

        for (int i = 0; i < 20; i++) {
            model_sess.run(Collections.singletonMap(inputName, inputTensor));
        }
        assertTrue(true);
        // return (float[][]) output.get(0).getValue();
    }


    @Test
    public void checkGPUAvailable() throws OrtException, IOException {
        String resources_path = System.getenv("RESOURCES_PATH");

        OrtEnvironment env = OrtEnvironment.getEnvironment();

        OrtSession.SessionOptions opts = new SessionOptions();
        opts.setOptimizationLevel(OptLevel.BASIC_OPT);
        opts.addCUDA(0);

        EnumSet<OrtProvider> pvs = env.getAvailableProviders();
        assertTrue(pvs.contains(OrtProvider.CUDA), String.format("Available providers: %s", pvs));
    }



    @Test 
    public void envTestTrue() {
        String path = System.getenv("RESOURCES_PATH");
        assertEquals("/home/ubuntu/resources", path, path);
    }


    @Test 
    public void envTestFalse() {
        String path = System.getenv("NONE_TEST_DFIEJKFVDHJK");
        assertEquals(null, path, path);
    }

    // @Test
    public void grpc_client_test() {

        // Create a channel to communicate with the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

        HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        GrpcTest.HelloResponse response = blockingStub.sayHello(GrpcTest.HelloRequest.newBuilder().setName("Alice").build());

        channel.shutdown();

        assertEquals("Hello, Alice", response.getGreeting());
    }


    // @Test
    public void onnxruntime_test() throws OrtException, IOException {
        String resources_path = System.getenv("RESOURCES_PATH");
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        assertNotEquals(null, env);

        OrtSession.SessionOptions opts = new SessionOptions();
        opts.setOptimizationLevel(OptLevel.BASIC_OPT);
        assertNotEquals(null, opts);

        OrtSession session = env.createSession(resources_path + "/mobilenetv2.onnx", opts);
        assertNotEquals(null, session);

        TensorInfo inputTensorInfo = (TensorInfo)session.getInputInfo().get("input").getInfo();
        long[] shape = inputTensorInfo.getShape();
        long[] expectedArr = { -1, 3, 224, 224 };

        assertArrayEquals(expectedArr, shape, String.format("shape and array"));

        String inputName = session.getInputNames().iterator().next();

        assertEquals("input", inputName);


        float[][][][] testData = new float[1][(int)shape[1]][(int)shape[2]][(int)shape[3]];
        for (float[][][] dim : testData) {
           for (float[][] channels : dim) {
               for (float[] rows : channels) {
                   for (float elem : rows ) {
                       elem = 0;
                   }
               }
           } 
        }

        OnnxTensor test = OnnxTensor.createTensor(env, testData);
        Result output = session.run(Collections.singletonMap(inputName, test));

        assertEquals(2, output.size());
    }


    @Test
    void base64DecodingTest() {
		String text = "hello world";
		byte[] targetBytes = text.getBytes();
        
        Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);
        
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedBytes);
        

		for(int i = 0; i < decodedBytes.length; i++){
			assertEquals(targetBytes[i], decodedBytes[i]);
		}

    }

	@Test 
	void base64ImageTest() throws IOException {
        String resources_path = System.getenv("RESOURCES_PATH");
        BufferedImage image = readImage(resources_path + "/tokkis.jpg");
        String base64Image = imageToBase64(image);
        BufferedImage decodedImage = base64ToImage(base64Image);
        
        if (image != null && decodedImage != null) {
            int img_width = image.getWidth();
            int img_height = image.getHeight();
            int dimg_width = decodedImage.getWidth();
            int dimg_height = decodedImage.getHeight();

            assertEquals(2880, img_width);
            assertEquals(1694, img_height);
            assertEquals(img_width, dimg_width);
            assertEquals(img_height, dimg_height);
        } else {
            assert(false);
        }

    }
    

    @Test
    void imageToRank3Tensor() throws IOException {

        String resources_path = System.getenv("RESOURCES_PATH");
        BufferedImage image = readImage(resources_path + "/tokkis.jpg");
        String base64Image = imageToBase64(image);
        BufferedImage decodedImage = base64ToImage(base64Image);

        int width = decodedImage.getWidth();
        int height = decodedImage.getHeight();

        float[] iimg = new float[width*height*3];
        for (int i = 0; i < iimg.length; i++) {
            iimg[i] = -1;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = decodedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                iimg[y*width + x] = (float)r;
                iimg[width*height + y*width + x] = (float)g;
                iimg[2*width*height + y*width + x] = (float)b;
            }
        }

        for (float p : iimg) {
            assertTrue(p >= -1.0f && p <= 256.0f, String.format("pixel is %f", p));
        }
    }

    // float[] bufferTofloatImage(BufferedImage decodedImage) throws IOException {
    //     int width = decodedImage.getWidth();
    //     int height = decodedImage.getHeight();

    //     float[] fimg = new float[width * height * 3];
    //     for (int i = 0; i < fimg.length; i++) {
    //         fimg[i] = -1;
    //     }

    //     for (int y = 0; y < height; y++) {
    //         for (int x = 0; x < width; x++) {
    //             int rgb = decodedImage.getRGB(x, y);
    //             int r = (rgb >> 16) & 0xFF;
    //             int g = (rgb >> 8) & 0xFF;
    //             int b = rgb & 0xFF;
    //             fimg[y * width + x] = (float) r;
    //             fimg[width * height + y * width + x] = (float) g;
    //             fimg[2 * width * height + y * width + x] = (float) b;
    //         }
    //     }

    //     return fimg;
    // }

    float[] bufferTofloatImage(BufferedImage decodedImage) throws IOException {
        int width = decodedImage.getWidth();
        int height = decodedImage.getHeight();

        float[] fimg = new float[width*height*3];
        for (int i = 0; i < fimg.length; i++) {
            fimg[i] = -1;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = decodedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                fimg[y*width + x] = (float)r;
                fimg[width*height + y*width + x] = (float)g;
                fimg[2*width*height + y*width + x] = (float)b;
            }
        }

        return fimg;
    }

    BufferedImage base64ToImage(String base64) throws IOException {
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(base64);
        ByteArrayInputStream decodedByteImage = new ByteArrayInputStream(decodedBytes);
        return ImageIO.read(decodedByteImage);
    }


    BufferedImage readImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        return ImageIO.read(imageFile);
    }

    String imageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        // Encode byte array to Base64
        return Base64.getEncoder().encodeToString(imageBytes);
    }



}
