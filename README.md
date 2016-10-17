Design Choices
-----------------------
I decided to make the class HtmlDocument to abstract away JSoup's Document class.  This means that it will be easy
to substitute a different HTML parser if necessary, because as long as HtmlDocument's API remains the same, the class
EmailCrawler does not need to be changed to fit a new parser, only HtmlDocument would need to be changed.