<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Image Gallery with Lightbox</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        header {
            text-align: center;
            padding: 30px 0;
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            color: white;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        h1 {
            font-size: 2.8rem;
            margin-bottom: 10px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        .tagline {
            font-size: 1.2rem;
            opacity: 0.9;
            max-width: 600px;
            margin: 0 auto;
        }

        /* Filter Categories */
        .filters {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 12px;
            margin-bottom: 30px;
        }

        .filter-btn {
            padding: 10px 22px;
            background-color: white;
            border: 2px solid #2575fc;
            color: #2575fc;
            border-radius: 50px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .filter-btn:hover {
            background-color: #2575fc;
            color: white;
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(37, 117, 252, 0.3);
        }

        .filter-btn.active {
            background-color: #2575fc;
            color: white;
        }

        /* Gallery Grid */
        .gallery {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }

        .gallery-item {
            position: relative;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            transition: all 0.4s ease;
            cursor: pointer;
            background-color: white;
        }

        .gallery-item:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
        }

        .gallery-item img {
            width: 100%;
            height: 240px;
            object-fit: cover;
            display: block;
            transition: transform 0.5s ease;
        }

        .gallery-item:hover img {
            transform: scale(1.05);
        }

        .image-info {
            padding: 20px;
        }

        .image-info h3 {
            font-size: 1.3rem;
            margin-bottom: 8px;
            color: #2c3e50;
        }

        .image-info p {
            color: #7f8c8d;
            font-size: 0.95rem;
        }

        .category-tag {
            display: inline-block;
            background-color: #e8f4fc;
            color: #3498db;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            margin-top: 10px;
        }

        /* Lightbox */
        .lightbox {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.9);
            z-index: 1000;
            justify-content: center;
            align-items: center;
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        .lightbox-content {
            max-width: 90%;
            max-height: 90%;
            border-radius: 8px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
        }

        .lightbox-info {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 20px;
            transform: translateY(0);
            transition: transform 0.3s ease;
        }

        .lightbox:hover .lightbox-info {
            transform: translateY(0);
        }

        .lightbox-nav {
            position: absolute;
            top: 50%;
            width: 100%;
            display: flex;
            justify-content: space-between;
            padding: 0 20px;
            transform: translateY(-50%);
        }

        .lightbox-nav button {
            background-color: rgba(255, 255, 255, 0.2);
            border: none;
            color: white;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            font-size: 1.5rem;
            cursor: pointer;
            transition: all 0.3s ease;
            backdrop-filter: blur(5px);
        }

        .lightbox-nav button:hover {
            background-color: rgba(255, 255, 255, 0.3);
            transform: scale(1.1);
        }

        .close-btn {
            position: absolute;
            top: 20px;
            right: 30px;
            color: white;
            font-size: 2.5rem;
            cursor: pointer;
            transition: transform 0.3s ease;
        }

        .close-btn:hover {
            transform: rotate(90deg);
        }

        /* Controls */
        .controls {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }

        .control-btn {
            padding: 12px 30px;
            background-color: #2575fc;
            color: white;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            font-weight: 600;
            font-size: 1rem;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .control-btn:hover {
            background-color: #1a68e8;
            transform: translateY(-3px);
            box-shadow: 0 7px 15px rgba(37, 117, 252, 0.3);
        }

        .control-btn:active {
            transform: translateY(0);
        }

        /* Footer */
        footer {
            text-align: center;
            padding: 30px 0;
            margin-top: 40px;
            color: #7f8c8d;
            border-top: 1px solid #eee;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            h1 {
                font-size: 2.2rem;
            }

            .gallery {
                grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
                gap: 20px;
            }

            .filters {
                gap: 8px;
            }

            .filter-btn {
                padding: 8px 16px;
                font-size: 0.9rem;
            }

            .lightbox-nav button {
                width: 40px;
                height: 40px;
                font-size: 1.2rem;
            }

            .controls {
                flex-direction: column;
                align-items: center;
            }

            .control-btn {
                width: 80%;
                justify-content: center;
            }
        }

        @media (max-width: 480px) {
            .gallery {
                grid-template-columns: 1fr;
            }

            .container {
                padding: 15px;
            }

            h1 {
                font-size: 1.8rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1><i class="fas fa-camera-retro"></i> Visual Gallery</h1>
            <p class="tagline">A responsive image gallery with lightbox view, filters, and smooth transitions</p>
        </header>

        <!-- Filter Categories -->
        <div class="filters">
            <button class="filter-btn active" data-filter="all">All Images</button>
            <button class="filter-btn" data-filter="nature">Nature</button>
            <button class="filter-btn" data-filter="city">City</button>
            <button class="filter-btn" data-filter="travel">Travel</button>
            <button class="filter-btn" data-filter="abstract">Abstract</button>
        </div>

        <!-- Image Gallery -->
        <div class="gallery">
            <!-- Nature Images -->
            <div class="gallery-item" data-category="nature">
                <img src="https://images.unsplash.com/photo-1501854140801-50d01698950b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Mountain Landscape">
                <div class="image-info">
                    <h3>Mountain Landscape</h3>
                    <p>Beautiful view of mountains with a serene lake reflection.</p>
                    <span class="category-tag">Nature</span>
                </div>
            </div>

            <div class="gallery-item" data-category="nature">
                <img src="https://images.unsplash.com/photo-1441974231531-c6227db76b6e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Forest Path">
                <div class="image-info">
                    <h3>Forest Path</h3>
                    <p>Sunlight filtering through trees in a dense forest.</p>
                    <span class="category-tag">Nature</span>
                </div>
            </div>

            <!-- City Images -->
            <div class="gallery-item" data-category="city">
                <img src="https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w-1200&q=80" alt="City Skyline">
                <div class="image-info">
                    <h3>City Skyline</h3>
                    <p>Modern city skyline during the golden hour.</p>
                    <span class="category-tag">City</span>
                </div>
            </div>

            <div class="gallery-item" data-category="city">
                <img src="https://images.unsplash.com/photo-1519501025264-65ba15a82390?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Urban Night">
                <div class="image-info">
                    <h3>Urban Night</h3>
                    <p>City lights reflecting on wet streets after rain.</p>
                    <span class="category-tag">City</span>
                </div>
            </div>

            <!-- Travel Images -->
            <div class="gallery-item" data-category="travel">
                <img src="https://images.unsplash.com/photo-1469474968028-56623f02e42e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Aerial View">
                <div class="image-info">
                    <h3>Aerial View</h3>
                    <p>Breathtaking aerial view of a coastal landscape.</p>
                    <span class="category-tag">Travel</span>
                </div>
            </div>

            <div class="gallery-item" data-category="travel">
                <img src="https://images.unsplash.com/photo-1506929562872-bb421503ef21?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Beach Paradise">
                <div class="image-info">
                    <h3>Beach Paradise</h3>
                    <p>Crystal clear water and white sandy beach.</p>
                    <span class="category-tag">Travel</span>
                </div>
            </div>

            <!-- Abstract Images -->
            <div class="gallery-item" data-category="abstract">
                <img src="https://images.unsplash.com/photo-1543857778-c4a1a569e388?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Colorful Abstract">
                <div class="image-info">
                    <h3>Colorful Abstract</h3>
                    <p>Vibrant colors blending in an abstract pattern.</p>
                    <span class="category-tag">Abstract</span>
                </div>
            </div>

            <div class="gallery-item" data-category="abstract">
                <img src="https://images.unsplash.com/photo-1550684376-efcbd6e3f031?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Geometric Shapes">
                <div class="image-info">
                    <h3>Geometric Shapes</h3>
                    <p>Modern geometric pattern with contrasting colors.</p>
                    <span class="category-tag">Abstract</span>
                </div>
            </div>

            <!-- Additional Images -->
            <div class="gallery-item" data-category="nature">
                <img src="https://images.unsplash.com/photo-1439066615861-d1af74d74000?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Waterfall">
                <div class="image-info">
                    <h3>Majestic Waterfall</h3>
                    <p>Powerful waterfall cascading down rocky cliffs.</p>
                    <span class="category-tag">Nature</span>
                </div>
            </div>

            <div class="gallery-item" data-category="travel">
                <img src="https://images.unsplash.com/photo-1488646953014-85cb44e25828?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Desert">
                <div class="image-info">
                    <h3>Desert Dunes</h3>
                    <p>Rolling sand dunes under a clear blue sky.</p>
                    <span class="category-tag">Travel</span>
                </div>
            </div>

            <div class="gallery-item" data-category="city">
                <img src="https://images.unsplash.com/photo-1470225620780-dba8ba36b745?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="City Bridge">
                <div class="image-info">
                    <h3>City Bridge</h3>
                    <p>Iconic bridge connecting two parts of the city.</p>
                    <span class="category-tag">City</span>
                </div>
            </div>

            <div class="gallery-item" data-category="abstract">
                <img src="https://images.unsplash.com/photo-1513360371669-4adf3dd7dff8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1200&q=80" alt="Liquid Art">
                <div class="image-info">
                    <h3>Liquid Art</h3>
                    <p>Colorful liquid creating unique abstract patterns.</p>
                    <span class="category-tag">Abstract</span>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <div class="controls">
            <button class="control-btn" id="prev-btn">
                <i class="fas fa-chevron-left"></i> Previous
            </button>
            <button class="control-btn" id="next-btn">
                Next <i class="fas fa-chevron-right"></i>
            </button>
        </div>

        <!-- Lightbox -->
        <div class="lightbox" id="lightbox">
            <span class="close-btn" id="close-lightbox">&times;</span>
            <img class="lightbox-content" id="lightbox-img" src="" alt="">
            <div class="lightbox-info">
                <h3 id="lightbox-title"></h3>
                <p id="lightbox-description"></p>
                <span class="category-tag" id="lightbox-category"></span>
            </div>
            <div class="lightbox-nav">
                <button id="lightbox-prev"><i class="fas fa-chevron-left"></i></button>
                <button id="lightbox-next"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>

        <footer>
            <p>Responsive Image Gallery &copy; 2023 | Built with HTML, CSS & JavaScript</p>
            <p>All images are sourced from Unsplash</p>
        </footer>
    </div>

    <script>
        // Gallery data
        const galleryItems = document.querySelectorAll('.gallery-item');
        const filterButtons = document.querySelectorAll('.filter-btn');
        const lightbox = document.getElementById('lightbox');
        const lightboxImg = document.getElementById('lightbox-img');
        const lightboxTitle = document.getElementById('lightbox-title');
        const lightboxDescription = document.getElementById('lightbox-description');
        const lightboxCategory = document.getElementById('lightbox-category');
        const closeLightbox = document.getElementById('close-lightbox');
        const lightboxPrev = document.getElementById('lightbox-prev');
        const lightboxNext = document.getElementById('lightbox-next');
        const prevBtn = document.getElementById('prev-btn');
        const nextBtn = document.getElementById('next-btn');
        
        let currentIndex = 0;
        let filteredItems = Array.from(galleryItems);
        
        // Filter images by category
        filterButtons.forEach(button => {
            button.addEventListener('click', () => {
                // Update active filter button
                filterButtons.forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');
                
                const filter = button.getAttribute('data-filter');
                
                // Filter gallery items
                galleryItems.forEach(item => {
                    const category = item.getAttribute('data-category');
                    
                    if (filter === 'all' || filter === category) {
                        item.style.display = 'block';
                        setTimeout(() => {
                            item.style.opacity = '1';
                            item.style.transform = 'translateY(0)';
                        }, 10);
                    } else {
                        item.style.opacity = '0';
                        item.style.transform = 'translateY(20px)';
                        setTimeout(() => {
                            item.style.display = 'none';
                        }, 300);
                    }
                });
                
                // Update filtered items array
                if (filter === 'all') {
                    filteredItems = Array.from(galleryItems);
                } else {
                    filteredItems = Array.from(galleryItems).filter(item => 
                        item.getAttribute('data-category') === filter
                    );
                }
                
                // Reset current index
                currentIndex = 0;
            });
        });
        
        // Open lightbox when clicking on gallery item
        galleryItems.forEach((item, index) => {
            item.addEventListener('click', () => {
                // Find the index in filtered items
                const itemCategory = item.getAttribute('data-category');
                const activeFilter = document.querySelector('.filter-btn.active').getAttribute('data-filter');
                
                if (activeFilter === 'all' || activeFilter === itemCategory) {
                    currentIndex = filteredItems.indexOf(item);
                } else {
                    currentIndex = 0;
                }
                
                updateLightbox();
                lightbox.style.display = 'flex';
                document.body.style.overflow = 'hidden'; // Prevent scrolling
            });
        });
        
        // Close lightbox
        closeLightbox.addEventListener('click', () => {
            lightbox.style.display = 'none';
            document.body.style.overflow = 'auto';
        });
        
        // Close lightbox when clicking outside the image
        lightbox.addEventListener('click', (e) => {
            if (e.target === lightbox) {
                lightbox.style.display = 'none';
                document.body.style.overflow = 'auto';
            }
        });
        
        // Navigation in lightbox
        lightboxPrev.addEventListener('click', (e) => {
            e.stopPropagation();
            navigateLightbox(-1);
        });
        
        lightboxNext.addEventListener('click', (e) => {
            e.stopPropagation();
            navigateLightbox(1);
        });
        
        // Keyboard navigation
        document.addEventListener('keydown', (e) => {
            if (lightbox.style.display === 'flex') {
                if (e.key === 'Escape') {
                    lightbox.style.display = 'none';
                    document.body.style.overflow = 'auto';
                } else if (e.key === 'ArrowLeft') {
                    navigateLightbox(-1);
                } else if (e.key === 'ArrowRight') {
                    navigateLightbox(1);
                }
            }
        });
        
        // Gallery navigation buttons
        prevBtn.addEventListener('click', () => {
            // Find the currently displayed items
            const activeFilter = document.querySelector('.filter-btn.active').getAttribute('data-filter');
            
            if (activeFilter === 'all') {
                filteredItems = Array.from(galleryItems);
            } else {
                filteredItems = Array.from(galleryItems).filter(item => 
                    item.getAttribute('data-category') === activeFilter
                );
            }
            
            // Navigate to previous image in filtered set
            if (filteredItems.length > 0) {
                currentIndex = (currentIndex - 1 + filteredItems.length) % filteredItems.length;
                filteredItems[currentIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
                
                // Add highlight effect
                filteredItems[currentIndex].style.boxShadow = '0 0 0 3px #2575fc';
                setTimeout(() => {
                    filteredItems[currentIndex].style.boxShadow = '';
                }, 1000);
            }
        });
        
        nextBtn.addEventListener('click', () => {
            // Find the currently displayed items
            const activeFilter = document.querySelector('.filter-btn.active').getAttribute('data-filter');
            
            if (activeFilter === 'all') {
                filteredItems = Array.from(galleryItems);
            } else {
                filteredItems = Array.from(galleryItems).filter(item => 
                    item.getAttribute('data-category') === activeFilter
                );
            }
            
            // Navigate to next image in filtered set
            if (filteredItems.length > 0) {
                currentIndex = (currentIndex + 1) % filteredItems.length;
                filteredItems[currentIndex].scrollIntoView({ behavior: 'smooth', block: 'nearest' });
                
                // Add highlight effect
                filteredItems[currentIndex].style.boxShadow = '0 0 0 3px #2575fc';
                setTimeout(() => {
                    filteredItems[currentIndex].style.boxShadow = '';
                }, 1000);
            }
        });
        
        // Helper functions
        function navigateLightbox(direction) {
            currentIndex = (currentIndex + direction + filteredItems.length) % filteredItems.length;
            updateLightbox();
        }
        
        function updateLightbox() {
            if (filteredItems.length === 0) return;
            
            const currentItem = filteredItems[currentIndex];
            const imgSrc = currentItem.querySelector('img').getAttribute('src');
            const title = currentItem.querySelector('h3').textContent;
            const description = currentItem.querySelector('p').textContent;
            const category = currentItem.querySelector('.category-tag').textContent;
            
            lightboxImg.setAttribute('src', imgSrc);
            lightboxImg.setAttribute('alt', title);
            lightboxTitle.textContent = title;
            lightboxDescription.textContent = description;
            lightboxCategory.textContent = category;
        }
        
        // Initialize
        updateLightbox();
    </script>
</body>
</html>