Saving mkdocs automation plan to file for refinement.

## Plan: Automate mkdocs.yml generation

TL;DR — Create a small generator that scans `docs/`, extracts titles (from front-matter or filenames), builds a `nav` tree, merges it with a template `mkdocs.template.yml`, and writes `mkdocs.yml`. Run the generator before builds (Netlify/CI) and optionally as a git hook so mkdocs config stays in sync automatically.

### Steps
1. Create a generator script `scripts/generate_mkdocs.py` that scans `docs/`, extracts titles (front-matter `title` if present, else humanized filename), preserves folder nesting, and sorts by numeric prefixes when present.  
2. Add a base config `mkdocs.template.yml` at repo root containing theme, plugins, extra config (everything except `nav`) and a placeholder comment for the generated `nav`.  
3. Make the script merge the generated `nav` into `mkdocs.template.yml` and output the final `mkdocs.yml` (overwrite or write atomically). Link: `mkdocs.template.yml` -> `mkdocs.yml`.  
4. Update build steps to run the generator before `mkdocs build`: modify `netlify.toml` build command to run `python scripts/generate_mkdocs.py && mkdocs build`, and add `requirements-netlify.txt` with `mkdocs mkdocs-material pyyaml python-frontmatter`. Files: `netlify.toml`, `requirements-netlify.txt`.  
5. Add an optional safety step: a git pre-commit or CI check `scripts/generate_mkdocs.py --check` that fails if generated `mkdocs.yml` would differ, so contributors run/commit the generation. Add a lightweight GitHub Action or Netlify pre-build validation if desired.

### Further Considerations
1. Commit vs. ephemeral: Do you want `mkdocs.yml` to be committed to the repo, or generated only at CI/build time? (Recommend committing for Netlify compatibility; CI can also check alignment.)  
2. Ordering and overrides: Support numeric prefixes for ordering, front-matter `weight` or `title`, and an optional `docs/_nav.yml` override file to force custom nav entries.  
3. Edge cases: Decide how to treat `README.md` (map to folder index), non-markdown files, and excluded directories (add exclude list in `scripts/generate_mkdocs.py`).

This is a draft — tell me:
- Do you prefer the generated `mkdocs.yml` to be committed or generated only at build time?  
- Any custom ordering rules or existing front-matter conventions I should follow?

