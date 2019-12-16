![test case gif](images/full-test-suite.gif "images/full-test-suite.gif")

Augmented Reality (AR) systems are widely expected to become a significant platform for software development in the near future, but effectively testing that software on those systems is challenging, both because of the size of the potential input set and the difficulty of controlling conditions under repeated tests. Previous research explored using virtual reality (VR) as a theoretical solution to evaluating AR usability under controlled conditions where aspects of a simulated AR application (registration and jitter) could be experimentally controlled. By surveying a subset of available AR application platforms to determine their capabilities, I identified one (Android) which was capable of supporting the use of 3D VR test fixtures in testing AR applications, demonstrating the validity of this approach for testing non-simulated AR applications. A trivial Android ARCore application was written, and the ability of these tests to identify and kill mutants are used to validate this approach. In addition, AR platform requirements for generalizing and improving the use of VR fixtures for automated testing of AR software are proposed. Adopting the proposed requirements to support automated testing is suggested as a necessary factor to foster the development of high-quality, reliable AR software ecosystems.

# Initial Criteria

Based on widespread commercial and open-source software testing practices, the search for appropriate AR SDKs began with the following requirements against which candidate systems were evaluated:

* Application and test source must, as much as possible, be stored in plain text in order to maintain ease-of-use with common version control systems and software development workflows.
* Builds and tests should be triggered via a command-line interface, rather than taking an action in a GUI, in order to maintain ease-of-use with common continuous integration (CI) environments.
* The fixture should always start in the same ground state even if it is used in multiple tests during the same test run.
* It should be possible to install, configure, and cache the test environment without manual interventions.
* It should be possible both to change the state of the application's environment and access information about application state from within executable test code.
* Pass/fail information should be returned to the same command-line interface used to start builds and tests, ideally with an exit code that can be used to automatically determine whether or not all tests have passed.

# Methodology

Several different AR platforms/SDKs were evaluated until a suitable one, Google's Android, was identified as supporting the initial criteria. If an Android Virtual Device (AVD) is [configured](https://developers.google.com/ar/develop/java/emulator#create_a_virtual_device_with_ar_support) with the appropriate settings to enable ARCore, the emulated hardaware – including the camera and the accelerometer – will be fed appropriate data as if the device is "in" a 3D environment. A small apartment (constructed with files sharing the prefix "Toren1BD") serves as the environment.

Then, a trivial AR application, a simplified navigation aid for the visually impaired, was developed. This application conducts hit tests using Google's ARCore framework to determine the distance of the first object encountered by an imaginary vector passing from the middle of the device to infinity, perpendicular to the device's major axis. It enters four different states depending on the distance of that object:

* Not close to anything (more than 5 meters from an object)
* Avoid the obstacle in N meters (where N is between 5 and 3 meters)
* Close (where the distance is between 1 and 3 meters )
* Stop! (where the object is less than 1 meter away)

Although not well-documented, there exists a facility for recording and re-playing movements in the 3D environment. Additionally, the emulated device is created with a special IP address (10.0.2.2) through which the emulator running the device can be sent commands. A handful of helper commands were developed to

# Lists
You can create unordered (bullet) and ordered (number) lists.
The initial character determines which type of list.
Just use the same character to avoid paragraph breaks between list items that occurs when you change the initial character as below.

* Unordered lists
* can use asterisks
- Or
- minuses
+ Or
+ pluses

Ordered lists will be automatically renumbered for you.
Just start them all with "1." so they are easy to rearrange.

1. Ordered lists start with a number and period.
1. Another item
1. Actual numbers don't matter, just that it's a number
1. And another item.

Indenting allows you to create lists or paragraphs within a list item.
The indentation should match the first character in the parent list item.

1. Top-level list item
   * Indented list item 1
   * Indented list item 2
1. Second top-level list item
   * more indented list items
   * and another
1. Third top-level list item

   You can also have properly indented paragraphs within list items.
   Notice the blank line above and the leading spaces to match the indentation.

   To have a line break without a paragraph, you will need to use two trailing spaces.
   Note that this line is separate, but within the same paragraph.


# Tables

Tables are rendered full width.
Table specifications include headings and justification for each column.

| Tables        | Are           | Cool  |
| :------------ |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

You can also use inline markdown like emphasis within table cells.

Markdown | Less | Pretty
--- | --- | ---
*Still* | `renders` | **nicely**
1 | 2 | 3


# Blockquotes

Blockquotes highlight quotations and other important information.
You may use emphasis and other inline markdown formatting in a blockquote.

> **Education is what remains after one has forgotten what one has learned in school.**
>
> _Albert Einstein_


# Code Highlights

You can highlight code within a line or on multiple lines.
You cannot put markdown within highlighted code.
* `Inline code` has "back-ticks" (`) around it.
* The code blocks start with three back-ticks (\`\`\`) on a separate line before the code
  and end with three back-ticks on a separate line after the code.
  No syntax highlighting is performed.


```Javascript
var s = "JavaScript";
alert(s);
```

Code  blocks preserve indentation.

```
    s = "Python"
    print s
```


# Horizontal Rule

You can create a horizontal rule (thin line) using three or more...

---

Hyphens

***

Asterisks

___

Underscores


# Headings

The behavior of headers depends on which type you specify in `site.json`.
Level 1 headers are drawn with a graphic border as above and are collapsible.
* In `page` mode, the other headers behave like normal markdown.
* In `card` mode, level 2-4 headers create a new card with the width determined by the heading level and the current screen width.
  They include a graphic border and are collapsible.
  Level 5 and 6 headers are treated normally.

Card widths vary with screen width to provide a responsive system.
Typical screen widths are:
* lg - > 991px, tablet landscape, laptop, desktop
* md - < 992px, tablet portrait
* sm - < 768px, phone landscape
* xs - < 576px, phone portrait

The page width is divided into twelve columns.
The heading level determines the number of columns used.
Cards are laid out in a row as long as they fit.
Cards on a line don't all need to be the same width.
If a card does not fit, it starts a new row and the process continues.

| heading level  | lg | md | sm | xs |
| :---: | :---: | :---: | :---: | :---: |
| 1 | 12 | 12 | 12 | 12 |
| 2 | 8 | 8 | 12 | 12 |
| 3 | 6 | 6 | 12 | 12 |
| 4 | 4 | 4 | 12 | 12 |

Change the width of the browser window on a desktop or laptop to view the following headings with various screen widths on the [**Card**](#/card) page.

# H1
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.

##### H5
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.

###### H6
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.


## H2-8
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.
#### H4-4
The quick brown fox jumped over the lazy dog.


### H3-6-1
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.
### H3-6-2
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.

#### H4-4-1
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.
#### H4-4-2
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.
#### H4-4-3
The quick brown fox jumped over the lazy dog.
The quick brown fox jumped over the lazy dog.


# Alt-headers
Alternatively, for H1 and H2, an underline-ish style:

Alt-H1
======

Alt-H2
------


# Links

There are a variety of methods to insert links in you content.
These links show the use of absolute and relative URLs.

[I'm an inline-style link](https://www.google.com)

[I'm an inline-style link with title](https://www.google.com "Google's Homepage")

[I'm a reference-style link][Arbitrary case-insensitive reference text]

[I'm a relative reference to the file containing this markdown](pages/markdown.md)

[You can use numbers for reference-style link definitions][1]

Or leave it empty and use the [link text itself].

URLs and URLs in angle brackets will automatically get turned into links. http://www.example.com or <http://www.example.com> for example.

Some text to show that the reference links can follow later in the markdown source.

[arbitrary case-insensitive reference text]:https://www.mozilla.org
[1]:http://slashdot.org
[link text itself]:http://www.reddit.com

To link to the menu items on this site, use `#/menuitem` as the destination URL.
These URLs work in browser history and can be bookmarked.
* Use `#/` to link to the [home](#/) (CS001) page.
* Use `#/faculty` to link to the [faculty](#/faculty) menu item that renders the page
* Use `pages/faculty.md` to link to the [unrendered raw text](pages/faculty.md) that defines the contents for the faculty page.



# Images

Here's some images using inline and reference style references.
Images are scaled according to the width of the page or block,
but will not be larger than their original size.
Reference style is useful when you us the same image multiple times on the same page so you only need to define the URL once.
Hover to see the image title text containing the relative URL used for the image.


![me](images/me.png "images/me.png")

![CS001][courselogo]

[courselogo]: ./images/course.png "./images/course.png"


# Videos

You can embed a video from YouTube or link to the corresponding YouTube page.
These look the same, but the behavior is different.
Other behaviors are also possible depending on the options in your action link.
In each case you need an image to click to perform the action,
hence the image link inside the action link.

[![DMB](http://img.youtube.com/vi/3c8uD6UeB9E/0.jpg)](http://www.youtube.com/embed/3c8uD6UeB9E)

[![DMB](http://img.youtube.com/vi/qjykrjAS5bQ/0.jpg)](http://www.youtube.com/watch?v=qjykrjAS5bQ)


# HTML

**HTML is not supported.
Learn to use markdown instead.**

<dl>
  <dt>Definition list</dt>
  <dd>Is something people use sometimes.</dd>

  <dt>HTML in Markdown</dt>
  <dd>Does *not* work.</dd>
</dl>


