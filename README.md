Build Instructions
-----------------------
To use the command line app, simply use run_windows.sh or run_unix.sh (depending on your operating system), with the
desired domain as an argument.  For example, the command "run_windows.sh web.mit.edu" would run the command line app on
a windows machine to find all email addresses discoverable from the domain web.mit.edu.

Please note that if a domain directs to a site but a different domain or its subdomain is typically used within the
links on the site (for example, mit.edu directs to the same page as web.mit.edu, but all links on the site use its
subdomain web.mit.edu), then this domain will not return email addresses other than those on the home page.  The one
exception for this is when the subdomain "www" is used in links on the site.  This is accounted for in the code since
it is the most typical subdomain that directs to the same site as its parent domain.