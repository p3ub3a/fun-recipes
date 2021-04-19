### fun-recipes

Functionalities managed by AspectJ / SpringAOP:
- the user can add new recipes and fetch existing ones. I used Aspectj (*fun-recipes-be/src/main/java/com/funrecipes/aop/Monitoring.aj*) to limit the number of GET and POST requests that can be made in a given time frame. New requests can be made after the time limit expires;
- the number of failed requests for each type (GET,POST,PUT,PATCH,DELETE) is stored in "request_info" table;
- after each successful request, a log is printed out.
