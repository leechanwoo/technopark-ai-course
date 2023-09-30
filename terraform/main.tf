provider "aws" {
  region = "ap-northeast-2"  
}

resource "aws_instance" "example_instance" {
  ami           = "ami-02288bc8778f3166f"  
  instance_type = "t2.micro"      
}
