# plounge-quoter

![ploungequote.png](http://68.media.tumblr.com/fa7e3912bdec55643d9ae280d34928fd/tumblr_nhkszdkeR71t4798zo1_540.png)

This is a little test project to hopefully improve my Kotlin abilities.
Plounge-Quoter is a program which should read in given text, and then overlay
that text on an image. It should also have the ability to submit the quote
directly to the ~~official~~ unofficial
[Plounge Quotes tumblr](http://ploungequotes.tumblr.com)
in such a way that formats the post to the standard.

For the vast majority of cases, the default parameter values will be fine.
However, in more complicated quotes, it's worthwhile to see what options are 
available by checking the reference.

Documentation for use is broken up into several parts:

* [Running the generator](doc/running.md)
* [Quote file examples](examples)
* [Global parameter reference](doc/global_params.md)
* [Quote parameter reference](doc/quote_params.md)

This readme instead provides details on how to actually build this program.

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