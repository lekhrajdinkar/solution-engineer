# Inverted Indexes
## Reference
- https://www.hellointerview.com/learn/courses/system-design/lesson/foundations/db-indexing#inverted-indexes

---
## Overview
- range + exact + **prefix/suffix** match query : BTree works well, 
  - `SELECT * FROM posts WHERE content LIKE 'database%'`
  - `SELECT * FROM posts WHERE content LIKE '%database'`
  - `SELECT * FROM posts WHERE created_at > ''xxxx-xx-xx';`
  - `SELECT * FROM posts WHERE created_at = ''xxxx-xx-xx';`
- what about  **full pattern matching** ? 
  - `SELECT * FROM posts WHERE content LIKE '%database%';`

> **inverted index** solves this by flipping the relationship between documents and their content.


```
- Instead of storing documents with their words, 
- it stores words with their documents

---
doc1: "B-trees are fast and reliable"
doc2: "Hash tables are fast but limited"
doc3: "B-trees handle range queries well"

---
b-trees  -> [doc1, doc3]
fast     -> [doc1, doc2]
reliable -> [doc1]
hash     -> [doc2]
tables   -> [doc2]
limited  -> [doc2]
handle   -> [doc3]
range    -> [doc3]
queries  -> [doc3]
```

---
## Real-World Examples
-  Elasticsearch index text

```
== analysis pipeline that processes and enriches the content ==

Breaking text into tokens (words or subwords)
Converting to lowercase
Removing common "stop words" like "the" or "and"
Often applying stemming (reducing words to their root form)
```

additional features
- Term **frequency** analysis (how often words appear)
- **Relevance** scoring (which documents best match the query)
- **Fuzzy** matching (finding close matches like "databas")
- **Phrase** queries (matching exact sequences of words)

## trade off
- Inverted indexes require **substantial storage overhead** and careful updating.
- When a document changes, you need to update entries for every term it contains.