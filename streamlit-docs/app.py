import streamlit as st
import os
from pathlib import Path
import re

# Page config
st.set_page_config(page_title="Docs Dashboard", layout="wide")

# Initialize session state
if 'expanded_folders' not in st.session_state:
    st.session_state.expanded_folders = set()
if 'selected_file' not in st.session_state:
    st.session_state.selected_file = None
if 'show_nav' not in st.session_state:
    st.session_state.show_nav = True

# CSS for styling
st.markdown("""
<style>
.main-header { font-size: 2rem; font-weight: bold; margin-bottom: 1rem; }
.section-link { cursor: pointer; color: #0066cc; text-decoration: underline; }
.section-link:hover { color: #004499; }
.fixed-nav {
    position: fixed;
    top: 140px;
    right: 20px;
    width: 280px;
    max-height: 400px;
    overflow-y: auto;
    background: white;
    padding: 15px;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    z-index: 1000;
    resize: both;
    /* Add cursor for drag handle */
}
.fixed-nav:hover {
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}
.fixed-nav .nav-header {
    margin-bottom: 10px;
    padding-bottom: 8px;
    border-bottom: 1px solid #eee;
    font-weight: bold;
    cursor: move;
    user-select: none;
}
.stButton {
    margin: 0 !important;
    padding: 0 !important;
}
div[data-testid="stButton"] {
    margin: 0 !important;
    padding: 0 !important;
    margin-bottom: -8px !important;
}
div[data-testid="stButton"] > button {
    width: 100%;
    text-align: left !important;
    justify-content: flex-start !important;
    border: none;
    background: transparent;
    padding: 2px 8px !important;
    border-radius: 4px;
    transition: all 0.2s;
    margin: 0 !important;
    height: 28px !important;
    min-height: 28px !important;
}
div[data-testid="stButton"] > button:hover {
    background-color: #f0f2f6;
    color: #0066cc;
}
.fixed-toggle-btn {
    position: fixed;
    top: 90px;
    right: 25px;
    z-index: 1100;
    background: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 50%;
    box-shadow: 0 2px 8px rgba(0,0,0,0.12);
    width: 44px;
    height: 44px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    font-size: 1.5rem;
    transition: box-shadow 0.2s;
}
.fixed-toggle-btn:hover {
    box-shadow: 0 4px 16px rgba(0,0,0,0.18);
    background: #f0f2f6;
}
</style>
""", unsafe_allow_html=True)

# Add draggable JS for .fixed-nav
st.markdown("""
<script>
(function() {
    function makeDraggable(el) {
        var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
        var header = el.querySelector('.nav-header');
        if (!header) return;
        header.onmousedown = dragMouseDown;
        function dragMouseDown(e) {
            e = e || window.event;
            e.preventDefault();
            pos3 = e.clientX;
            pos4 = e.clientY;
            document.onmouseup = closeDragElement;
            document.onmousemove = elementDrag;
        }
        function elementDrag(e) {
            e = e || window.event;
            e.preventDefault();
            pos1 = pos3 - e.clientX;
            pos2 = pos4 - e.clientY;
            pos3 = e.clientX;
            pos4 = e.clientY;
            el.style.top = (el.offsetTop - pos2) + "px";
            el.style.left = (el.offsetLeft - pos1) + "px";
            el.style.right = "auto";
        }
        function closeDragElement() {
            document.onmouseup = null;
            document.onmousemove = null;
        }
    }
    function waitForNav() {
        var nav = document.querySelector('.fixed-nav');
        if (nav) {
            makeDraggable(nav);
        } else {
            setTimeout(waitForNav, 300);
        }
    }
    waitForNav();
})();
</script>
""", unsafe_allow_html=True)

def build_tree_structure(path, base_path):
    """Build hierarchical tree structure"""
    items = []
    
    try:
        for item in sorted(path.iterdir()):
            if item.is_dir():
                rel_path = item.relative_to(base_path)
                folder_item = {
                    'type': 'folder',
                    'name': item.name,
                    'path': str(rel_path),
                    'full_path': str(item),
                    'children': build_tree_structure(item, base_path)
                }
                items.append(folder_item)
            elif item.suffix == '.md':
                rel_path = item.relative_to(base_path)
                file_item = {
                    'type': 'file',
                    'name': item.stem,
                    'path': str(rel_path),
                    'full_path': str(item)
                }
                items.append(file_item)
    except PermissionError:
        pass
    
    return items

def get_docs_structure():
    docs_path = Path("../docs")
    all_files = []
    
    if not docs_path.exists():
        return [], all_files
    
    structure = build_tree_structure(docs_path, docs_path)
    
    def collect_files(items):
        files = []
        for item in items:
            if item['type'] == 'file':
                files.append(item)
            elif item['type'] == 'folder':
                files.extend(collect_files(item['children']))
        return files
    
    all_files = collect_files(structure)
    return structure, all_files

def extract_sections(content):
    """Extract sections from markdown content for right navigation"""
    sections = []
    lines = content.split('\n')
    for i, line in enumerate(lines):
        if line.startswith('#'):
            level = len(line) - len(line.lstrip('#'))
            title = line.lstrip('# ').strip()
            if title:
                sections.append({
                    'level': level, 
                    'title': title, 
                    'id': title.lower().replace(' ', '-').replace('(', '').replace(')', ''),
                    'line': i
                })
    return sections

def search_files(all_files, query):
    """Search files by name and content"""
    if not query:
        return all_files
    
    results = []
    query_lower = query.lower()
    
    for file_data in all_files:
        if query_lower in file_data['name'].lower():
            results.append(file_data)
            continue
        
        try:
            with open(file_data['full_path'], 'r', encoding='utf-8') as f:
                content = f.read().lower()
                if query_lower in content:
                    results.append(file_data)
        except:
            pass
    
    return results

def render_tree_item(item, level=0, filtered_files=None):
    """Render tree item with proper indentation"""
    indent = "  " * level
    
    if item['type'] == 'folder':
        if filtered_files is not None:
            has_files = any(f['path'].startswith(item['path']) for f in filtered_files)
            if not has_files:
                return
        
        is_expanded = item['path'] in st.session_state.expanded_folders
        expand_icon = "🔽" if is_expanded else "▶️"
        folder_icon = "📂" if is_expanded else "📁"
        
        folder_label = f"{indent}{expand_icon} {folder_icon} {item['name']}"
        if st.button(folder_label, key=f"folder_{item['path']}"):
            if is_expanded:
                st.session_state.expanded_folders.discard(item['path'])
            else:
                st.session_state.expanded_folders.add(item['path'])
            st.rerun()
        
        if is_expanded:
            for child in item['children']:
                render_tree_item(child, level + 1, filtered_files)
    
    elif item['type'] == 'file':
        if filtered_files is not None and item not in filtered_files:
            return
            
        file_label = f"{indent}  📄 {item['name']}"
        if st.button(file_label, key=f"file_{item['path']}"):
            st.session_state.selected_file = item
            st.rerun()

def extract_sections_with_children(content):
    """Extract sections and their children for tab rendering, supporting ##, ###, ####, and #####"""
    lines = content.split('\n')
    sections = []
    current_section = None
    current_subsection = None
    current_subsubsection = None
    for i, line in enumerate(lines):
        if line.startswith('## '):
            if current_section:
                if current_subsection:
                    if current_subsubsection:
                        current_subsubsection['end'] = i
                        current_subsection['children'].append(current_subsubsection)
                        current_subsubsection = None
                    current_subsection['end'] = i
                    current_section['children'].append(current_subsection)
                    current_subsection = None
                current_section['end'] = i
                sections.append(current_section)
            current_section = {'title': line.lstrip('# ').strip(), 'start': i, 'children': []}
        elif line.startswith('### ') and current_section:
            if current_subsection:
                if current_subsubsection:
                    current_subsubsection['end'] = i
                    current_subsection['children'].append(current_subsubsection)
                    current_subsubsection = None
                current_subsection['end'] = i
                current_section['children'].append(current_subsection)
            current_subsection = {'title': line.lstrip('# ').strip(), 'start': i, 'children': []}
        elif line.startswith('#### ') and current_section and current_subsection:
            if current_subsubsection:
                current_subsubsection['end'] = i
                current_subsection['children'].append(current_subsubsection)
            current_subsubsection = {'title': line.lstrip('# ').strip(), 'start': i, 'children': []}
        elif line.startswith('##### ') and current_section and current_subsection and current_subsubsection:
            # treat ##### as children of ####
            current_subsubsection['children'].append({'title': line.lstrip('# ').strip(), 'line': i})
    if current_section:
        if current_subsection:
            if current_subsubsection:
                current_subsubsection['end'] = len(lines)
                current_subsection['children'].append(current_subsubsection)
            current_subsection['end'] = len(lines)
            current_section['children'].append(current_subsection)
        current_section['end'] = len(lines)
        sections.append(current_section)
    return sections, lines

def render_content(file_path):
    """Render markdown content with tabs for rendered and raw content, and tabs for each ## and ### section"""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        sections, lines = extract_sections_with_children(content)
        tab_titles = ["📝 Raw Content"]
        if sections:
            tab_titles += [s['title'] for s in sections]
        tabs = st.tabs(tab_titles)
        with tabs[0]:
            st.code(content, language="markdown")
        if sections:
            for idx, section in enumerate(sections):
                with tabs[idx+1]:
                    section_lines = lines[section['start']:section['end']]
                    st.markdown(f"## {section['title']}")
                    if section['children']:
                        subtab_titles = [c['title'] for c in section['children']]
                        subtabs = st.tabs(subtab_titles)
                        for sidx, child in enumerate(section['children']):
                            with subtabs[sidx]:
                                child_lines = section_lines[child['start']-section['start']:child.get('end', section['end'])-section['start']]
                                st.markdown(f"### {child['title']}")
                                # If this child has #### children, show as sub-sub-tabs
                                if child.get('children'):
                                    subsubtab_titles = [g['title'] for g in child['children']]
                                    subsubtabs = st.tabs(subsubtab_titles)
                                    for ssidx, gchild in enumerate(child['children']):
                                        with subsubtabs[ssidx]:
                                            # If gchild has 'line', it's a ##### section, else it's a ####
                                            if 'line' in gchild:
                                                g_start = gchild['line']-child['start']+1
                                                g_end = child['children'][ssidx+1]['line']-child['start'] if ssidx+1 < len(child['children']) and 'line' in child['children'][ssidx+1] else len(child_lines)
                                                g_content = '\n'.join(child_lines[g_start:g_end]).strip()
                                                st.markdown(f"##### {gchild['title']}")
                                                if g_content:
                                                    st.markdown(g_content)
                                            else:
                                                # gchild is a #### section with possible ##### children
                                                gchild_lines = child_lines[gchild['start']-child['start']:gchild.get('end', child['end'])-child['start']]
                                                st.markdown(f"#### {gchild['title']}")
                                                if gchild.get('children'):
                                                    subsubsubtab_titles = [h['title'] for h in gchild['children']]
                                                    subsubsubtabs = st.tabs(subsubsubtab_titles)
                                                    for hidx, hchild in enumerate(gchild['children']):
                                                        with subsubsubtabs[hidx]:
                                                            if 'line' in hchild:
                                                                h_start = hchild['line']-gchild['start']+1
                                                                h_end = gchild['children'][hidx+1]['line']-gchild['start'] if hidx+1 < len(gchild['children']) and 'line' in gchild['children'][hidx+1] else len(gchild_lines)
                                                                h_content = '\n'.join(gchild_lines[h_start:h_end]).strip()
                                                                st.markdown(f"##### {hchild['title']}")
                                                                if h_content:
                                                                    st.markdown(h_content)
                                                else:
                                                    gchild_content = '\n'.join(gchild_lines[1:]).strip()
                                                    if gchild_content:
                                                        st.markdown(gchild_content)
                                # If no ####, show all content under ###
                                else:
                                    child_content = '\n'.join(child_lines[1:]).strip()
                                    if child_content:
                                        st.markdown(child_content)
                    else:
                        # If no ###, show all section content
                        section_content = '\n'.join(section_lines[1:]).strip()
                        if section_content:
                            st.markdown(section_content)
        else:
            with tabs[0]:
                st.markdown(content)
    except Exception as e:
        st.error(f"Error reading file: {e}")

docs_structure, all_files = get_docs_structure()

if not docs_structure:
    st.warning("No docs folder found. Please ensure docs folder exists in parent directory.")
    st.stop()

with st.sidebar:
    st.markdown("## 🔍 Global Search")
    search_query = st.text_input("Search files...", key="global_search")
    filtered_files = search_files(all_files, search_query) if search_query else all_files
    col1, col2, col3 = st.columns(3)
    with col1:
        if st.button("➕", key="expand_all", help="Expand All"):
            def collect_all_folders(items):
                folders = []
                for item in items:
                    if item['type'] == 'folder':
                        folders.append(item['path'])
                        folders.extend(collect_all_folders(item['children']))
                return folders
            st.session_state.expanded_folders = set(collect_all_folders(docs_structure))
            st.rerun()
    with col2:
        if st.button("➖", key="collapse_all", help="collapse All"):
            st.session_state.expanded_folders = set()
            st.rerun()
    with col3:
        toggle_icon = "❌" if st.session_state.show_nav else "📋"
        if st.button(toggle_icon, key="nav_toggle_fixed", help="Toggle page content Navigation"):
            st.session_state.show_nav = not st.session_state.show_nav
            st.rerun()

    st.markdown('---')
    for item in docs_structure:
        render_tree_item(item, 0, filtered_files if search_query else None)

if st.session_state.selected_file:
    file_data = st.session_state.selected_file
    st.markdown(f"### 📄 {file_data['name']}")
    st.markdown(f"*{file_data['path']}*")
    render_content(file_data['full_path'])
else:
    st.info("👈 Select a file from the sidebar to view its content")
    
    if search_query:
        st.markdown(f"### 🔍 Search Results for '{search_query}'")
        if filtered_files:
            st.markdown(f"Found {len(filtered_files)} files:")
            for file_data in filtered_files[:10]:
                st.markdown(f"- **{file_data['name']}** in *{file_data['path']}*")
        else:
            st.markdown("No files found matching your search.")