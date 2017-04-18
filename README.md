# Resourceloading for I18N/I10N

For a real world application, at least text resources have to be (inter)nationalized. Usually this is not a complicated tast. You have to have some external resource file with key - value pairs. The values are either text resources or lists of text resources. In the first case, you have to have a resource file for every supported language, in the second case all languages are supported in on file. I prefere the first method (makes for smaller files).

A real world application has several thousand text resources. I was interested if Elm can handle that without problem (have a look at the generated JavaScript code and you will see what I was worried about).

This experiment consists of two parts. One is a small REST server written in Java, just for delivering a JSON resource file with 2000 entries. The second part is an Elm programm that calls the REST service and parses the JSON file into an Elm record using decoder pipes.

