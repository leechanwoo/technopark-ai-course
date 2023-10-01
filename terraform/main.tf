provider "aws" {
  region = "ap-northeast-2"  
}

resource "aws_instance" "ai_web_application" {
  ami           = "ami-02288bc8778f3166f"  
  instance_type = "t2.large"

  key_name = aws_key_pair.cicd_make_keypair.key_name
  vpc_security_group_ids = [aws_security_group.cicd_sg.id]
  associate_public_ip_address = true

  user_data = file("setup.sh")
}
