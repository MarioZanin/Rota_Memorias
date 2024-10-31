// Valida se os nomes contêm apenas letras
function validateNames(name) {
    const regex = /^[A-Za-z\s]+$/;
    return regex.test(name);
}

// Valida se a data de falecimento não é no futuro
function validateDeathDate(deathDate) {
    const today = new Date().toISOString().split('T')[0];
    return deathDate <= today;
}

// Função de busca
function search() {
    const deceasedName = document.getElementById('deceasedName').value;
    const cemeteryName = document.getElementById('cemeteryName').value;
    const deathDate = document.getElementById('deathofDate').value;
    const motherName = document.getElementById('motherName').value;

    // Valida os nomes
    if (!validateNames(deceasedName) || !validateNames(motherName)) {
        alert('Os nomes devem conter apenas letras.');
        return;
    }

    // Valida a data de falecimento
    if (deathDate && !validateDeathDate(deathDate)) {
        alert('A data de falecimento não pode ser no futuro.');
        return;
    }

    const searchData = {
        nome: deceasedName,
        nomeCemiterio: cemeteryName,
        dataFalecimento: deathDate,
        nomeMae: motherName
    };

    fetch('/api/falecido/search', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(searchData)
    })
    .then(response => response.json())
    .then(data => {
        displayResults(data);
    })
    .catch(error => {
        console.error('Erro ao buscar dados:', error);
    });
}

// Função para exibir os resultados da busca
function displayResults(data) {
    const resultsContainer = document.getElementById('results');
    resultsContainer.innerHTML = '';

    if (data.length === 0) {
        resultsContainer.innerHTML = '<p>Nenhum resultado encontrado.</p>';
        return;
    }

    const table = document.createElement('table');
    table.innerHTML = `
        <thead>
            <tr>
                <th>Nome do Falecido</th>
                <th>Nome do Cemitério</th>
                <th>Data de Falecimento</th>
                <th>Nome da Mãe</th>
                <th>Foto</th>
            </tr>
        </thead>
        <tbody>
            ${data.map(item => `
                <tr>
                    <td>${item.nome}</td>
                    <td>${item.nomeCemiterio}</td>
                    <td>${item.dataFalecimento}</td>
                    <td>${item.nomeMae}</td>
                    <td><img src="${item.foto ? item.foto : 'imagens/Pessoa.jpg'}" alt="Foto de ${item.nome}" width="50"></td>
                </tr>
            `).join('')}
        </tbody>
    `;

    resultsContainer.appendChild(table);
}

// Função de logout
function logout() {
    console.log('Logout');
    // Aqui pode-se implementar a lógica de logout, como redirecionar para uma página de login
}

// Redireciona para a página de localização do falecido
function deceasedResult() {
    window.location.href = 'deceased-location.html';
}

// Redireciona para a página de condolências
function condolencesResult() {
    window.location.href = 'condolences.html';
}

// Redireciona para a página de informações sobre o cemitério
function cemeteryResult() {
    window.location.href = 'cemetery-info.html';
}

// Envia uma mensagem de condolência
function sendCondolence() {
    const senderName = document.getElementById('senderName').value;
    const message = document.getElementById('condolencesMessage').value;
    const urlParams = new URLSearchParams(window.location.search);
    const deceasedId = urlParams.get('deceasedId');

    if (senderName && message) {
        const condolence = {
            senderName: senderName,
            message: message,
            deceasedId: deceasedId
        };

        fetch('/api/mensagem', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(condolence)
        })
        .then(response => {
            if (response.ok) {
                alert('Condolência enviada com sucesso!');
                // Redirecionar para outra página se necessário
            } else {
                alert('Falha ao enviar a condolência.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao enviar a condolência.');
        });
    } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
    }
}
