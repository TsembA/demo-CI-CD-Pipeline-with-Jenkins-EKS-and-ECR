package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "<html><head><style>\n"
             + "  body { text-align:center; font-family:sans-serif; background-color:#f9f9f9; }\n"
             + "  .animated-bg {\n"
             + "    position: fixed;\n"
             + "    top: 0;\n"
             + "    left: 0;\n"
             + "    width: 100%;\n"
             + "    height: 100%;\n"
             + "    z-index: -1;\n"
             + "    overflow: hidden;\n"
             + "  }\n"
             + "  .bg-img {\n"
             + "    position: absolute;\n"
             + "    width: 200px;\n"
             + "    animation: moveAcross 15s linear infinite;\n"
             + "  }\n"
             + "  @keyframes moveAcross {\n"
             + "    from { left: -200px; top: 50%; }\n"
             + "    to { left: 100%; top: 50%; }\n"
             + "  }\n"
             + "</style></head><body>\n"
             + "<h1>🐔 Java Maven app is running on <span style='color:#4CAF50;'>EKS</span>!</h1>\n"
             + "<p>Containerized with Docker & deployed to AWS EKS 🚀</p>\n"
             + "<div class='animated-bg'>\n"
             + "  <img class='bg-img' src='/animated-bg.png' alt='Animated Scene'>\n"
             + "</div>\n"
             + "<h3>Uptime: <span id='uptime'>00:00:00</span></h3>\n"
             + "<script>\n"
             + "  let seconds = 0;\n"
             + "  function updateClock() {\n"
             + "    seconds++;\n"
             + "    const hrs = String(Math.floor(seconds / 3600)).padStart(2, '0');\n"
             + "    const mins = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');\n"
             + "    const secs = String(seconds % 60).padStart(2, '0');\n"
             + "    document.getElementById('uptime').textContent = `${hrs}:${mins}:${secs}`;\n"
             + "  }\n"
             + "  setInterval(updateClock, 1000);\n"
             + "</script>\n"
             + "</body></html>";
    }

    @GetMapping("/status")
    public String status() {
        return "<html><body style='text-align:center; font-family:sans-serif;'>"
             + "<h2>App Status: <span style='color:green;'>OK</span> ✅</h2>"
             + "<img src='https://cdn-icons-png.flaticon.com/512/5968/5968672.png' width='100' alt='Spring Boot'/>"
             + "</body></html>";
    }
}
