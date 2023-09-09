
curl \
-v \
-X POST \
-H "Content-Type: application/json" \
-d '{
  "id":2,
  "title":"My Second Blog Post",
  "desc":"My Second Blog Post",
  "status":"IDEA",
  "contentType":"ARTICLE",
  "dateCreated":"2023-09-06T06:46:44.701631551",
  "dateUpdated":null,
  "url":""
}' \
http://localhost:8080/api/content
