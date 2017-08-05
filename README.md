# plounge-quoter

![ploungequote.png](http://68.media.tumblr.com/fa7e3912bdec55643d9ae280d34928fd/tumblr_nhkszdkeR71t4798zo1_540.png)

This is a little test project to hopefully improve my Kotlin abilities.
Plounge-Quoter is a program which should read in given text, and then overlay
that text on an image. It should also have the ability to submit the quote
directly to the official [Plounge Quotes tumblr](http://ploungequotes.tumblr.com) in such a way that formats the
post to the standard.

## Usage

The program is still in Alpha, but you can currently make an image with the
executable Jar file. Try running:

```bash
$ java -jar target/ploungequoter.jar -q path/to/quotefile
```

## Building

This project is organised and built using [Apache
Maven](https://maven.apache.org/).

To generate an executable jar file, run

```bash
$ mvn package
```

To compile the project without packaging, run:

```bash
$ mvn compile
```

## Maintainer Documentation
To generate Dokka documentation (a KDoc generator), run

```bash
$ mvn dokka:dokka
```

Documentation will then appear in `target/dokka`
