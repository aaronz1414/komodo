Build Instructions
-----------------------
To use the command line app, simply use run_windows.sh or run_unix.sh (depending on your operating system), with the
desired domain as an argument.  For example, the command "run_windows.sh web.mit.edu" would run the command line app on
a windows machine to find all email addresses discoverable from the domain web.mit.edu.

Please note that you 

Design Choices
-----------------------
I decided to make the class HtmlDocument to abstract away JSoup's Document class.  This means that it will be easy
to substitute a different HTML parser if necessary, because as long as HtmlDocument's API remains the same, the class
EmailCrawler does not need to be changed to fit a new parser, only HtmlDocument would need to be changed.