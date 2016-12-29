# md5zip - content checksums for zip files

A simple utility that calculates checksum for zip based on file names and contents, but ignoring timestamps of files.
Just running `md5sum foo.zip` would consider the timestamps of the files in checksum, which makes it problematic when
calculating checksum for build artifacts.

## Usage

```
java -jar md5sum.jar foo.zip
```
