document.addEventListener("DOMContentLoaded", function () {
  const homeLink = document.querySelector('a.md-header-nav__button');  // or inspect your theme's selector
  if (homeLink) {
    homeLink.setAttribute('href', 'https://front-end-docs.netlify.app');
  }
});
