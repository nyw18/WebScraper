# WebScraper

A Java webscraper application that will print a unique list of up to MAX_URLS URLs.
URLs are found by searching through HTML pages starting with the URL passed through the parameter.
Once the end of a page is reached, an already found but unaccessed URL is traversed for links until MAX_URLS is reached or all links have been accessed.
