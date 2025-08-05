<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>System Engineering 2025 Dashboard</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <style>
    body {
      background-image: url('https://images.unsplash.com/photo-1504384308090-c894fdcc538d');
      background-size: cover;
      background-position: center;
      background-attachment: fixed;
    }
    .backdrop {
      background-color: rgba(255, 255, 255, 0.85);
      backdrop-filter: blur(6px);
    }
  </style>
</head>
<body class="min-h-screen p-6 font-sans">
  <div class="max-w-5xl mx-auto backdrop rounded-xl p-6 shadow-xl">
    <h1 class="text-4xl font-bold mb-8 text-center text-blue-800">System Engineering 2025</h1>

    <!-- Dashboard Sections -->
    <div class="grid md:grid-cols-3 gap-6">
      
      <!-- Frontend -->
      <a href="https://front-end-docs.netlify.app/01_ng/000_ng_evolution-2025/" target="_blank"
         class="bg-white p-6 rounded-2xl shadow-lg hover:shadow-blue-500 transition-shadow duration-300 border border-gray-200">
        <h2 class="text-xl font-semibold mb-4 text-blue-700">HTML | SCSS | JavaScript | Angular </h2>
        <img src="https://skillicons.dev/icons?i=angular,ts,css,html,js,redux,rxjs,npm,nodejs" alt="Frontend stack" />
      </a>

      <!-- Backend -->
      <a href="https://lekhrajdinkar-backend.onrender.com" target="_blank"
         class="bg-white p-6 rounded-2xl shadow-lg hover:shadow-green-500 transition-shadow duration-300 border border-gray-200">
        <h2 class="text-xl font-semibold mb-4 text-green-700">Java | Docker & k8s |  AWS</h2>
        <img src="https://skillicons.dev/icons?i=java,aws,terraform,docker,kubernetes,git,github,linux,bash" alt="Backend stack" />
      </a>

      <!-- Python + System Design + GenAI -->
      <a href="https://system-engineering-notes-2025.netlify.app/" target="_blank"
         class="bg-white p-6 rounded-2xl shadow-lg hover:shadow-purple-500 transition-shadow duration-300 border border-gray-200">
        <h2 class="text-xl font-semibold mb-4 text-purple-700">Python | System Design | GenAI</h2>
        <img src="https://skillicons.dev/icons?i=python,fastapi" alt="Python and GenAI stack" />
      </a>
    </div>
  </div>
</body>
</html>
