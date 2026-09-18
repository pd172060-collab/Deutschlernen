// German Language Learning Web App JS
document.addEventListener('DOMContentLoaded', () => {
    console.log('DeutschLernen App Loaded - Part 1');

    // Highlight active nav item
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
});

