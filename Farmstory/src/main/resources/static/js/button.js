// document.addEventListener('DOMContentLoaded', function() {
//     // Select all the links inside cate_lnb
//     const links = document.querySelectorAll('.cate_lnb li a');
//
//     // Add click event listener to each link
//     links.forEach(link => {
//         link.addEventListener('click', function() {
//             console.log("link : "+link);
//             // Change the background image of the clicked link to '_ov.png'
//             const currentBg = window.getComputedStyle(link).backgroundImage;
//             if (currentBg.includes('.png')) {
//                 const newBg = currentBg.replace('.png', '_ov.png');
//                 link.style.backgroundImage = newBg;
//             }
//
//             // Optionally, reset the background image of all other links
//             links.forEach(otherLink => {
//                 if (otherLink !== link) {
//                     const otherBg = window.getComputedStyle(otherLink).backgroundImage;
//                     if (otherBg.includes('_ov.png')) {
//                         const resetBg = otherBg.replace('_ov.png', '.png');
//                         otherLink.style.backgroundImage = resetBg;
//                     }
//                 }
//             });
//         });
//     });
// });
