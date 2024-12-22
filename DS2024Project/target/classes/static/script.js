async function performSearch(inputId) {
    const keyword = document.getElementById(inputId).value.trim();
    if (!keyword) {
        alert('請輸入搜尋關鍵字！');
        return;
    }

    console.log('Performing search with keyword:', keyword);

    // 隱藏初始頁面，顯示結果頁面
    document.getElementById('initialSearchPage').classList.add('hidden');
    document.getElementById('resultsPage').classList.remove('hidden');

    // 發送 API 請求到後端
    try {
        const response = await fetch(`/search?keyword=${encodeURIComponent(keyword)}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log('API request sent, awaiting response...');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const results = await response.json();
        console.log('API response received:', results);

        // 更新結果計數
        const resultCount = results.length;
        document.getElementById('resultCount').textContent = `找到 ${resultCount} 個結果`;

        // 模擬推薦關鍵字（可以根據實際情況從 API 獲取）
        document.getElementById('suggestedKeyword1').textContent = `與 "${keyword}" 相關: 推薦詞1`;
        document.getElementById('suggestedKeyword2').textContent = `與 "${keyword}" 相關: 推薦詞2`;

        // 顯示搜索結果
        const resultsList = document.getElementById('resultsList');
        resultsList.innerHTML = "";
        results.forEach(result => {
            const resultItem = document.createElement("div");
            resultItem.className = "result-card bg-white rounded-lg p-6 shadow-sm hover:shadow-md transition-shadow";
            resultItem.innerHTML = `
                <h2 class="text-xl font-semibold text-blue-600 mb-2">
                    <a href="${result.url}" target="_blank" class="hover:underline">${result.title}</a>
                </h2>
                <p class="text-gray-600 text-sm truncate">${result.url}</p>
            `;
            resultsList.appendChild(resultItem);
        });

    } catch (error) {
        console.error('Error fetching search results:', error);
        console.error('Error details:', error);
        alert(`搜尋過程中發生錯誤，請稍後再試。錯誤訊息: ${error.message}`);
    }
}
