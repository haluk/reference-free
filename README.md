The hash functions should be independent and uniformly distributed (e.g. murmur, fnv, **Jenkins Hash**)

apache-maven build system should be installed.

Build
-----
cd /tmp
git clone https://github.com/haluk/reference-free.git
cd reference-free
mvn clean install
java -jar target/reference-free-1.0-SNAPSHOT.jar data/test.fasta