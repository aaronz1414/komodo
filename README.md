Build Instructions
-----------------------
To use the command line app, from within the project directory simply use run_windows.sh or run_unix.sh (depending on
your operating system), with the desired domain as an argument.  For example, the command "run_windows.sh web.mit.edu"
would run the command line app on a windows machine to find all email addresses discoverable from the domain
web.mit.edu.

Additional Information
-----------------------
Please note that if a domain directs to a site but a different domain or its subdomain is typically used within the
links on the site (for example, mit.edu directs to the same page as web.mit.edu, but all links on the site use its
subdomain web.mit.edu), then this domain will not return email addresses other than those on the home page. For example,
you must pass in web.mit.edu as an argument instead of mit.edu, or www.jana.com instead of jana.com, in order to
retrieve emails other than those on the home page.