# Quote Parameters

ploungequoter has quite a lot of settings for customisation. The following
parameters are supported currently.

### alignment

Determines the text alignment/justification of the quote content.

**Available values:**
```
alignment: left
alignment: right
alignment: centre
```
**Default:** `centre`

### anchor

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

### contentfontsize

The font size of the quote content, in points. Must be an integer.

**Syntax:**
```
contentfontsize: 40
```
**Default:** `80`

### position

Determines the position of the quote anchor.
Depending on [[postiontype]], can either
be in pixels, or in relative image coordinates. Accepts floating point values.

Note, `0.0,0.0` is the top left corner of the quote.

**Syntax:**
```
position: x,y
```
**Default:** `0.5,0.1`

### positiontype

** Not supported in this commit. **

### typeface

The typeface of the quote, both content and author. The font must be installed
on your system, and must be called with the given name.

To see all available fonts, run PloungeQuoter with only the `-f` flag.

**Syntax:**
```
typeface: Times New Roman
```
**Default:** `SansSerif`