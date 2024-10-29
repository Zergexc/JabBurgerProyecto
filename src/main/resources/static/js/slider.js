document.addEventListener('DOMContentLoaded', function() {
    const slider = document.querySelector('.header-slider .slider');
    const slides = document.querySelectorAll('.header-slider .slide');
    const prevBtn = document.querySelector('.header-slider .prev-btn');
    const nextBtn = document.querySelector('.header-slider .next-btn');
    let currentIndex = 0;

    function goToSlide(index) {
        if (index < 0) index = slides.length - 1;
        if (index >= slides.length) index = 0;
        slider.style.transform = `translateX(-${index * 100}%)`;
        currentIndex = index;
    }

    prevBtn.addEventListener('click', () => goToSlide(currentIndex - 1));
    nextBtn.addEventListener('click', () => goToSlide(currentIndex + 1));

    // Auto-slide every 5 seconds
    setInterval(() => goToSlide(currentIndex + 1), 5000);
});