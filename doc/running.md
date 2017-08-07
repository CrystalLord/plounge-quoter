# Running Plounge Quoter

Running PloungeQuoter is quite easy. It supports two options currently:

```
ploungequoter [-f][-q QUOTE_FILE]
```

### The "-q" Option (Normal Usage)

This option specifies a quote file. A quotefile is a text file with a 
specific format that can easily be read by the PloungeQuoter internal parser.

Examples of how to structure PloungeQuoter quote files can be found in the 
[examples directory](../examples), and documentation for each of the options 
in a quote file can be found in the [quote params](quote_params.md) doc.

If you just built the program from source, you can run it from the top level 
maven directory with the following command:

```bash
$ java -jar target/ploungequoter.jar -q path/to/quotefile
```

There are alternative ways of running the program (like with plain `kotlin`) 
and without packaging, but if you know how to use those, I assume you know 
enough to run the program with them.

### The "-f" option

In some cases, it can be hard to find all the available fonts on a system. To
 check what fonts are actually available, the `-f` flag exists. This will 
 simply print out all the installed font names on your computer. These can 
 then be used along with the [typeface parameter](quote_params.md#typeface).
