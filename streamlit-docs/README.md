# Streamlit Docs Dashboard

A minimal Streamlit application that creates a documentation dashboard by reading all markdown files from the docs folder.

## Features

- **Header**: Clean dashboard title
- **Tabs**: Each top-level docs folder becomes a tab
- **Side Navigation**: File selection within each tab
- **Main Content Area**: Renders markdown with full formatting support
- **Right Navigation**: Shows page sections for easy navigation
- **Responsive**: Supports text, images, links, tables, and all markdown elements

## Quick Start

```bash
pip install streamlit==1.29.0
```

```bash
streamlit run app.py
```

## Structure

The app automatically reads from `../docs/` and creates:
- Tabs for each folder (e.g., `00_cse`, `01_revision`, etc.)
- Sidebar navigation for files within each folder
- Main content area with markdown rendering
- Right sidebar with section navigation

## Usage

1. Navigate between folders using tabs
2. Select files from the sidebar
3. View content in the main area
4. Use right navigation for page sections

Access at: http://localhost:8501