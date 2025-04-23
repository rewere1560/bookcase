function smoothScrollBy(distance, duration, onDone) {
  const start = window.scrollY;
  const startTime = performance.now();

  function scroll(currentTime) {
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 1);
    window.scrollTo(0, start + distance * easeInOutQuad(progress));

    if (progress < 1) {
      requestAnimationFrame(scroll);
    } else if (onDone) {
      onDone();
    }
  }

  requestAnimationFrame(scroll);

  function easeInOutQuad(t) {
    return t < 0.5
      ? 2 * t * t
      : -1 + (4 - 2 * t) * t;
  }
}

function startScrollLoop(interval, scrollDistance) {
    function scrollStep() {
        smoothScrollBy(scrollDistance, 1000, () => {
            const atBottom = window.innerHeight + window.scrollY >= document.body.scrollHeight;
            if (atBottom) {
                setTimeout(() => {
                    window.scrollTo({ top: 0, behavior: 'smooth' });
                    setTimeout(() => scrollStep(), interval);
                }, interval/10);
            } else {
                setTimeout(scrollStep, interval);
            }
        });
    }

   scrollStep();
}