
curl \
-v \
-X PUT \
-H "Content-Type: application/json" \
-d '{
  "id":1,
  "title":"My First Blog Post ",
  "desc":"My First Blog Post",
  "status":"IDEA",
  "contentType":"ARTICLE",
  "dateCreated":"2023-09-06T06:46:44.701631551",
  "dateUpdated":null,
  "url":""
}' \
http://localhost:8080/api/content/99
