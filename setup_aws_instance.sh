sudo apt-get update -y
sudo apt-get install build-essential -y
sudo apt install default-jdk -y
sudo apt install maven -y

git clone https://github.com/ishpuniani/google2.0.git

cd google2.0
rm -rf trec_eval-9.0.7

wget https://trec.nist.gov/trec_eval/trec_eval-9.0.7.tar.gz

tar -xf trec_eval-9.0.7.tar.gz

cd trec_eval-9.0.7/

make

cd ..