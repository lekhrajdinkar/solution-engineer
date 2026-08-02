# Senior System Engineer (SSE)
## Docs by Roles (6)
- [2021-2025](docs/2022-2025)
- [2026-2030](docs/2026-2030)

---
## Blended  roles
```
- `DE` | Data Engineer
- `SE` | Solution Engineer
- `PE` | Platform Engineer
- `CE` | Cloud Engineer
---
- `AI` | AI Engineer
- `FDE` | Forward Deployment Engineer 💠💠
```

---
## Generating mkdocs.yml
> Files ending with `__x.md` will be skipped
```bash
# pip install -r requirements-netlify.txt
# - mkdocs 
# - mkdocs-material

uv add -r requirements-netlify.txt
python scripts/generate_mkdocs.py
mkdocs serve

# .\scripts\generate_mkdocs.bat
```
[http://localhost:8000](http://localhost:8000)
