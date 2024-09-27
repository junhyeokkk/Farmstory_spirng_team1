window.onload = function () {

    console.log("ddd");

    // 제품 타입 불러오기
    const category = document.querySelector('.category');

    fetch('/Farmstory/admin/prodcate', {
        method: 'GET'

    })
        .then(resp => resp.json())
        .then(data => {
            console.log(data);

            for (const prodcate of data) {
                const prodcate_opt = document.createElement('option');
                prodcate_opt.setAttribute('value', prodcate.prodCateNo);
                prodcate_opt.setAttribute('name', 'prodCateNo');
                prodcate_opt.innerText = prodcate.prodCateName;
                category.appendChild(prodcate_opt);
            }

        })
        .catch(err => {
            console.log(err);
        })

    // 할인율 동적 생성
    const discount = document.querySelector('.discount');

    for(let i=5; i<=50; i+=5) {
        const discount_opt = document.createElement('option');
        discount_opt.setAttribute('value', i);
        discount_opt.setAttribute('name', 'discount');
        discount_opt.innerText = i+'%';
        discount.appendChild(discount_opt);
    }

    function showFileName(input, targetId) {
            const fileName = input.files.length > 0 ? input.files[0].name : "No file chosen";
            document.getElementById(targetId).textContent = fileName;
        }

}