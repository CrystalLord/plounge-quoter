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

The style of the font, e.g. plain, italic, bold. Note that not all fonts 
support all possible styles.

**Available Values:**
```
authorfontstyle: plain
authorfontstyle: italic
authorfontstyle: bold
authorfontstyle: italicbold
```
**Default:** `italic`

## authormargin

The size of the author margins. The margins are symmetrical, and in pixels. 
You specify the left/right side margins, then the top/bottom margins 
individually. The values must be integers.

**Syntax:**
```
authormargin: LEFTRIGHT,TOPBOTTOM
```
**Default:** `10,10`

## authorposition

The position of the author text. Unlike the content as a whole, there are 
more limited options for the author text position currently.

Attached positions are where the author text is connected to the content text.
Otherwise, the author text is placed in some global position. Author position
 is determined using this value and by [authormargin](#authormargin).
 
**Available Values:**
```
authorposition: botleft
authorposition: botright
authorposition: botleftattached <NOT SUPPORTED YET>
authorposition: botrighttattached <NOT SUPPORTED YET>
```
**Default:** `botleft`


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

## contentfontstyle

The font style (plain, italics, bold) of the entire content block.

**Available Values:**
```
contentfontstyle: plain
contentfontstyle: italic
contentfontstyle: bold
contentfontstyle: italicbold
```
**Default:** `plain`

## contentwrap

Determines if there should be content smart wrapping, and where that wrapping 
should occur. It takes in two values, the left boundary and the right 
boundary relative coordinates.

To set wrapping at the edges of the image, set `0.0,1.0`. If you want 
wrapping with margins, set something like `0.1,0.9`, which will give a 10% 
margin on either side of the image.

If you do not wish for any smart wrapping, do not use this parameter.

**Syntax:**
```
contentwrap: LEFT,RIGHT
```
**Default:** Turned off.

## fillcolour

Set the fill colour of the text. Must be a list of 4 integers between 0 and 255.

**Syntax:**
```
fillcolour: RED,GREEN,BLUE,ALPHA
```
**Default:** `255,255,255,255`

## outlinecolour

Set the outline colour of the text. Must be a list of 4 integers between 0 and 
255.

**Syntax:**
```
outlinecolour: RED,GREEN,BLUE,ALPHA
```
**Default:** `0,0,0,255`


## position

Determines the position of the quote anchor.
Depending on [postiontype](#positiontype), can either
be in pixels, or in relative image coordinates. Accepts floating point values.

Note, `0.0,0.0` is the top left corner of the image.

**Syntax:**
```
position: X,Y
```
**Default:** `0.5,0.1`

## positiontype

Determines whether the value from [position](#position) is in absolute pixel 
coordinates or in relative image coordinates. In relative coordinates, 
(`img`), `position: 0.5,0.5` would mean the centre of the image, while in 
absolute (`abs`) coordinates, this would be rounded to the origin (`0,0`).

**Available Values:**
```
positiontype: img
positiontype: abs
```
**Default:** `img`

## typeface

The typeface of the quote, both content and author. The font must be installed
on your system, and must be called with the given name. If the font could not
 be found, the generator will default to `SansSerif`.

To see all available fonts, run PloungeQuoter with only the `-f` flag.

**Syntax:**
```
typeface: Times New Roman
```
**Default:** `SansSerif`