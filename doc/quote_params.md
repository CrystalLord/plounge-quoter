# Quote Parameters

Ploungequoter has quite a lot of settings for customisation. The following
parameters are supported currently.

Note, parameter values remove all leading and trailing white space.
This means that you can put as much spacing as you please anywhere in a quote
file.

## alignment

Determines the text alignment/justification of the quote content.

**Available values:**
```
alignment: left
alignment: right
alignment: centre
```
**Default:** `centre`

## anchor

Determines the anchor position type of the content of the quote.

**Available values:**
```
anchor: topleft
anchor: topright
anchor: botleft
anchor: botright
anchor: topcentre
anchor: botcentre
anchor: centrecentre
```
**Default:** `topcentre`

## author

The author text of the quote. Supports newline syntax with `\n`

**Syntax:**
```
author: ~NAME
```
**Defualt:** Empty.

## authorfontsize

The font size of the author text, in points.

**Syntax:**
```
authorfontsize: NUM
```
**Default:** `60`

## authorfontstyle

The style of the font, e.g. plain, italic, bold.

**Available Values:**
```
authorfontstyle: plain
authorfontstyle: italic
authorfontstyle: bold
authorfontstyle: italicbold
```
**Default:** `italic`

## content

The actual content of the quote. Supports newline syntax with `\n`. You're
encouraged to use backslashes here to continue on lines.

**Syntax:**
```
content: The quote I want to render is this \
and I'm quite proud of it.\nI want newlines in it too.
```
**Default:** Empty.

## contentfontsize

The font size of the quote content, in points. Must be an integer.

**Syntax:**
```
contentfontsize: NUM
```
**Default:** `80`

## position

Determines the position of the quote anchor.
Depending on [[postiontype]], can either
be in pixels, or in relative image coordinates. Accepts floating point values.

Note, `0.0,0.0` is the top left corner of the quote.

**Syntax:**
```
position: X,Y
```
**Default:** `0.5,0.1`

## positiontype

**Not supported in this commit.**

## typeface

The typeface of the quote, both content and author. The font must be installed
on your system, and must be called with the given name.

To see all available fonts, run PloungeQuoter with only the `-f` flag.

**Syntax:**
```
typeface: Times New Roman
```
**Default:** `SansSerif`