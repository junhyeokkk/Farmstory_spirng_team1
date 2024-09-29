window.onload = function () {

    const btnComplete = document.getElementById('btnComplete');
    const commentForm = document.commentForm;
    const commentList = document.getElementsByClassName('commentList')[0];

    btnComplete.onclick = async function (e) {
        e.preventDefault();

        const jsonData = {
            "writer": commentForm.writer.value,
            "parent": commentForm.parent.value,
            "content": commentForm.content.value,
            "user" : {
                "uid": commentForm.writer.value,
                "nick": commentForm.nick.value
            }
        }
        const data = await fetchPost('/comment/write', jsonData)

        if(data.no){
            alert('댓글이 등록되었습니다.')
            console.log(data.user.uid)
            // 동적 태그 생성
            const commentArticle =  `<article>
                                                <span class="nick">${data.user.nick}</span>
                                                <span class="date">${data.date}</span>
                                                <p class="content">${data.content}</p>
                                                <div>
                                                    <a href="#" class="remove">삭제</a>
                                                    <a href="#" class="modify">수정</a>
                                                </div>
                                       </article>`;
            commentList.insertAdjacentHTML('beforeend', commentArticle);
            commentForm.reset();

            location.href="/sboard/article/view?no="+ commentForm.parent.value+"&pg="+commentForm.pg.value;
        }else{
            alert('댓글 등록 실패');
        }


    }



}