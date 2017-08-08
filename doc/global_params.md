# Global Parameters

Global parameters do not belong to any one specific text object/quote. 
Instead, they belong to the whole image. These are important to make a 
quotefile, most notably, the source and background parameters.

**NOTICE:** Global parameters must be above any `quote` lines. They do not 
belong to any quote object, so they must come first. e.g.

```
background: my/file/path

quote
quoteparam: value
```

## background

This is the background image of the entire file. It can be both a path to a 
local file, or a URL. Both are supported.

If it's a URL, the file will be downloaded to your system. Using this same 
URL in further renderings will not require a download, as it checks a temp
 directory first if the file exists.
 
As of this commit, the downloaded files *are not deleted*. Please be aware of
 this.
 
**Syntax:**
```
background: https://site.com/background.png
background: http://site.com/background.jpg
background: /local/file/here
```
**Default:** There is no default. Plounge Quoter will complain.

## source

The URL/description of where the discussion took place. It *should* be a URL 
to say a reddit comment or a imgur image, but it but need not be.

Note, this is currently not supported in this commit.

```
source: SOURCE
```
**Default:** `No Source Given`
