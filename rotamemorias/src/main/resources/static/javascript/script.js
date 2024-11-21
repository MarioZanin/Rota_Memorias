// Função de validação para nomes
function validateNameInput(inputElement) {
    inputElement.addEventListener('input', function () {
        // Permitir apenas letras, espaços e caracteres como á, é, ç, etc.
        this.value = this.value.replace(/[^a-zA-ZÀ-ÿ\s'-]/g, '');
    });
}

// Aplicar a validação aos campos de nome
const deceasedNameInput = document.getElementById('deceasedName');
const cemeteryNameInput = document.getElementById('cemeteryName');
const motherNameInput = document.getElementById('motherName');
if (deceasedNameInput) validateNameInput(deceasedNameInput);
if (cemeteryNameInput) validateNameInput(cemeteryNameInput);
if (motherNameInput) validateNameInput(motherNameInput);

// Configurar para data no formato yyyy-MM-dd
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// Configurar a data máxima no campo de data de óbito
const deathDateInput = document.getElementById('deathDate');
if (deathDateInput) {
    const currentDate = new Date();
    deathDateInput.setAttribute('max', formatDate(currentDate));
}

// Função para converter data de yyyy-MM-dd para dd-MM-yyyy
function convertDateToDisplayFormat(dateString) {
    if (!dateString) return 'Não informado';
    const [year, month, day] = dateString.split('-');
    return `${day}-${month}-${year}`;
}

// Função de busca
function search() {
    const deceasedName = document.getElementById('deceasedName').value.trim();
    const cemeteryName = document.getElementById('cemeteryName').value.trim();
    const deathDate = document.getElementById('deathDate').value;
    const motherName = document.getElementById('motherName').value.trim();

    if (!deceasedName) {
        alert('Por favor, preencha o nome do falecido.');
        return;
    }

    const queryParams = new URLSearchParams();
    queryParams.append('nome', deceasedName);
    if (cemeteryName) queryParams.append('nomeCemiterio', cemeteryName);
    if (deathDate) queryParams.append('dataFalecimento', deathDate);
    if (motherName) queryParams.append('nomeMae', motherName);

    fetch(`/api/falecido/search?${queryParams.toString()}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na conexão com o servidor.');
            }
            return response.json();
        })
        .then(data => {
            if (data && data.length > 0) {
                const result = data[0];
                localStorage.setItem('searchResults', JSON.stringify({
                    deceasedId: result.id, // Armazena o ID do falecido
                    deceasedPhoto: result.foto || 'imagens/Pessoa.jpg',
                    deceasedName: result.nome || 'Nome não informado',
                    deceasedDetails: {
                        nomePai: result.nomePai || 'Não informado',
                        nomeMae: result.nomeMae || 'Não informado',
                        dataNascimento: convertDateToDisplayFormat(result.dataNascimento),
                        dataFalecimento: convertDateToDisplayFormat(result.dataFalecimento),
                        profissao: result.profissao || 'Não informado',
                        localizacaoSepultura: result.localizacaoSepultura || 'Não informado'
                    },
                    cemeteryPhoto: result.cemiterio?.foto || 'imagens/Cemiterio.jpg',
                    cemeteryName: result.cemiterio?.nome || 'Não informado',
                    cemeteryDetails: {
                        rua: result.cemiterio?.rua || 'Não disponível',
                        numero: result.cemiterio?.numero || 'Não disponível',
                        bairro: result.cemiterio?.bairro || 'Não disponível',
                        cidade: result.cemiterio?.cidade || 'Não disponível',
                        estado: result.cemiterio?.estado || 'Não disponível',
                        horariosFuncionamento: result.cemiterio?.horariosFuncionamento || 'Não disponível',
                        telefone: result.cemiterio?.telefone || 'Não disponível',
                        paginaOficial: result.cemiterio?.paginaOficial || '',
                        localizacaoCemiterio: result.cemiterio?.localizacao || 'Não disponível'
                    }
                }));

// Validação adicional
const searchResults = JSON.parse(localStorage.getItem('searchResults'));
console.log('searchResults:', searchResults);

if (!searchResults || !searchResults.deceasedId) {
    console.error('Erro: deceasedId não está configurado corretamente.');
    alert('Erro ao salvar os resultados. Por favor, tente novamente.');
    return;
}


                // Redirecionar para a página de resultados
                window.location.href = 'search.html';
            } else {
                alert('Nenhum resultado encontrado. Tente novamente.');
            }
        })
        .catch(error => {
            console.error('Erro ao buscar dados:', error);
            alert('Houve um problema ao buscar os dados. Tente novamente mais tarde.');
        });
}

// Função para exibir os resultados no search.html
function displayResults() {
    const results = JSON.parse(localStorage.getItem('searchResults')) || {};

    const deceasedPhoto = results.deceasedPhoto || 'imagens/Pessoa.jpg';
    const deceasedName = results.deceasedName || 'Nome não informado';
    const deceasedDetails = results.deceasedDetails || {};

    const cemeteryPhoto = results.cemeteryPhoto || 'imagens/Cemiterio.jpg';
    const cemeteryName = results.cemeteryName || 'Cemitério não informado';
    const cemeteryDetails = results.cemeteryDetails || {};

    const searchResults = document.getElementById('search-results');
    const dateSection1 = searchResults.querySelectorAll('.date-section')[0]; // Para o falecido
    const dateSection2 = searchResults.querySelectorAll('.date-section')[1]; // Para o cemitério

    // Exibição do falecido
    dateSection1.innerHTML = `
        <img id="deceasedPhoto" src="${deceasedPhoto}" alt="Foto do Falecido" class="result-photo">
        <div class="deceased-info">
            <strong id="deceasedName">${deceasedName}</strong>
            <p id="deceasedDetails">
                Nome do Pai: ${deceasedDetails.nomePai}<br>
                Nome da Mãe: ${deceasedDetails.nomeMae}<br>
                Data de Nascimento: ${deceasedDetails.dataNascimento}<br>
                Data de Falecimento: ${deceasedDetails.dataFalecimento}<br>
                Profissão: ${deceasedDetails.profissao}<br>
                Localização da Campa: ${deceasedDetails.localizacaoSepultura}
            </p>
            <button id="deceased-button" onclick="window.location.href='deceased-info.html?location=${encodeURIComponent(deceasedDetails.localizacaoSepultura)}'">
                Localização da Campa
            </button>
        </div>
    `;

    // Exibição do cemitério
    dateSection2.innerHTML = `
        <img id="cemeteryPhoto" src="${cemeteryPhoto}" alt="Fachada do Cemitério" class="result-image">
        <div class="cemetery-info">
            <strong id="cemeteryName">${cemeteryName}</strong>
            <p id="cemeteryDetails">
                Rua: ${cemeteryDetails.rua}<br>
                Número: ${cemeteryDetails.numero}<br>
                Bairro: ${cemeteryDetails.bairro}<br>
                Cidade: ${cemeteryDetails.cidade}<br>
                Estado: ${cemeteryDetails.estado}<br>
                Horário de Funcionamento: ${cemeteryDetails.horariosFuncionamento}<br>
                Telefone: ${cemeteryDetails.telefone}<br>
                Página Oficial: ${cemeteryDetails.paginaOficial}<br>
                Localização: ${cemeteryDetails.localizacaoCemiterio}
            </p>
            <button id="cemetery-button" onclick="window.location.href='cemetery-info.html?location=${encodeURIComponent(cemeteryDetails.localizacaoCemiterio)}'">
                Localização do Cemitério
            </button>
        </div>
    `;
}

function displayDeceasedInfo() {
    const searchResults = JSON.parse(localStorage.getItem('searchResults'));

    // Elementos da página
    const deceasedNameElement = document.getElementById('deceasedName');
    const deceasedPhotoElement = document.getElementById('deceasedPhotoResult');
    const graveLocationElement = document.getElementById('graveLocation');

    // Definindo as informações padrão caso não haja resultados
    const defaultDeceasedPhoto = 'imagens/Pessoa.jpg';
    const defaultDeceasedName = 'Falecido Não Encontrado';
    const defaultGraveLocation = 'Localização Não Disponível';

    if (!searchResults || Object.keys(searchResults).length === 0) {
        // Caso não haja dados no localStorage
        deceasedNameElement.textContent = defaultDeceasedName;
        deceasedPhotoElement.src = defaultDeceasedPhoto;
        graveLocationElement.textContent = defaultGraveLocation;
    } else {
        // Preenchendo as informações do falecido
        deceasedNameElement.textContent = searchResults.deceasedName;
        deceasedPhotoElement.src = searchResults.deceasedPhoto;

        // Localização da campa
        graveLocationElement.textContent = searchResults.deceasedDetails.localizacaoSepultura || 'Localização Não Informada';
    }
}

// Função para preencher informações do cemitério na página cemetery-info.html
function displayCemeteryInfo() {
    const cemeteryData = JSON.parse(localStorage.getItem('searchResults'));

    const defaultCemeteryDetails = {
        nome: "Cemitério não encontrado",
        rua: "Não disponível",
        numero: "Não disponível",
        bairro: "Não disponível",
        cidade: "Não disponível",
        estado: "Não disponível",
        foto: "../imagens/Cemiterio.jpg"
    };

    // Seleciona elementos da página
    const cemeteryNameElement = document.getElementById('cemeteryName');
    const cemeteryAddressElement = document.getElementById('cemeteryAddress');
    const cemeteryImageElement = document.getElementById('cemeteryImageResult');

    if (!cemeteryData || !cemeteryData.cemeteryName) {
        // Preenche com dados padrão
        cemeteryNameElement.textContent = defaultCemeteryDetails.nome;
        cemeteryAddressElement.innerHTML = `
            Endereço: ${defaultCemeteryDetails.rua}, ${defaultCemeteryDetails.numero} - Bairro: ${defaultCemeteryDetails.bairro}<br>
            Cidade: ${defaultCemeteryDetails.cidade} - Estado: ${defaultCemeteryDetails.estado}`;
        cemeteryImageElement.src = defaultCemeteryDetails.foto;
    } else {
        // Preenche com dados reais do localStorage
        const cemeteryDetails = cemeteryData.cemeteryDetails || {};
        cemeteryNameElement.textContent = cemeteryData.cemeteryName;
        cemeteryAddressElement.innerHTML = `
            Endereço: ${cemeteryDetails.rua || 'Não disponível'}, ${cemeteryDetails.numero || 'Não disponível'} - Bairro: ${cemeteryDetails.bairro || 'Não disponível'}<br>
            Cidade: ${cemeteryDetails.cidade || 'Não disponível'} - Estado: ${cemeteryDetails.estado || 'Não disponível'}`;
        cemeteryImageElement.src = cemeteryData.cemeteryPhoto || defaultCemeteryDetails.foto;
    }
}

function displayMoreInfo() {
    const defaultCemeteryPhoto = '../imagens/Cemiterio.jpg';
    const searchResults = JSON.parse(localStorage.getItem('searchResults'));

    // Configurações padrões caso não haja dados
    const defaultData = {
        cemeteryPhoto: defaultCemeteryPhoto,
        cemeteryName: 'Cemitério não informado',
        cemeteryDetails: {
            horariosFuncionamento:'Não disponível',
            telefone: 'Não disponível',
            paginaOficial: 'Não disponível',
            localizacaoCemiterio: 'Não disponível'
        },
    };

    const results = searchResults || defaultData;

    // Atualiza os campos da página
    document.getElementById('cemeteryPhotoResult').src = results.cemeteryPhoto || defaultCemeteryPhoto;
    document.getElementById('cemeteryName').innerText = results.cemeteryName || 'Nome não disponível';
    document.getElementById('cemeteryPhone').innerText = results.cemeteryDetails.telefone || 'Não disponível';
    document.getElementById('cemeteryWebsite').innerText = results.cemeteryDetails.paginaOficial || 'Não disponível';
    document.getElementById('cemeteryFuncionamento').innerText = results.cemeteryDetails.horariosFuncionamento || 'Não disponível';
    document.getElementById('cemeteryLocalizacao').innerText = results.cemeteryDetails.localizacaoCemiterio || 'Não disponível';
}







// Função para exibir as informações do falecido na página de condolências
function displayDeceasedInCondolencePage() {
    const searchResults = JSON.parse(localStorage.getItem('searchResults')) || {};

    // Elementos do DOM
    const deceasedPhotoElement = document.getElementById('deceasedPhotoResult');
    const deceasedNameElement = document.getElementById('deceasedName');

    // Informações do falecido
    const deceasedPhoto = searchResults.deceasedPhoto || '../imagens/Pessoa.jpg';
    const deceasedName = searchResults.deceasedName || 'Nome não informado';

    // Atualizar DOM
    deceasedPhotoElement.src = deceasedPhoto;
    deceasedNameElement.textContent = deceasedName;
}


// Função para configurar o envio de condolências
function setupCondolenceForm() {
    const sendButton = document.getElementById('sendCondolenceButton');
    sendButton.addEventListener('click', async () => {
        const senderName = document.getElementById('senderName').value.trim();
        const condolenceMessage = document.getElementById('condolenceMessage').value.trim();
        
        // Recuperar o falecido_id do LocalStorage
        const searchResults = JSON.parse(localStorage.getItem('searchResults')) || {};
        const deceasedId = searchResults.deceasedId;

        // Logs para depuração
        console.log('Nome do Remetente:', senderName);
        console.log('Mensagem:', condolenceMessage);
        console.log('Falecido ID:', deceasedId);

        // Verificar se os campos obrigatórios estão preenchidos
        if (!senderName || !condolenceMessage || !deceasedId) {
            alert('Por favor, preencha todos os campos obrigatórios.');
            return;  // Retorna para não continuar a execução se os campos estiverem vazios
        }

        const condolenceData = {
            nomeRemetente: senderName,
            mensagem: condolenceMessage,
            falecidoId: deceasedId,  // Relaciona a mensagem ao falecido
            dataEnvio: new Date().toISOString().split('T')[0], // Formato yyyy-MM-dd
        };

console.log("Dados enviados para a API:", condolenceData);

        try {
            // Enviar condolência via API
            const response = await fetch('/api/mensagens', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(condolenceData),
            });

            if (!response.ok) {
                throw new Error('Erro ao enviar condolência.');
            }

            // Exibir mensagem de sucesso
            alert('Sua mensagem foi enviada com sucesso!');
            window.location.href = 'messages.html'; // Redireciona para ver as mensagens enviadas
        } catch (error) {
            // Se ocorrer algum erro, logar e exibir a mensagem de erro
            console.error('Erro:', error);
            alert('Não foi possível enviar a mensagem. Tente novamente.');
        }
    });
}


// Função para carregar as últimas mensagens de condolências
function loadRecentMessages() {
    const searchResults = JSON.parse(localStorage.getItem('searchResults')) || {};
    const deceasedId = searchResults.deceasedId;

    if (!deceasedId) {
        alert('Nenhum falecido associado. Retornando à pesquisa.');
        window.location.href = 'index.html';
        return;
    }

    fetch(`/api/mensagens/${deceasedId}?limit=5`, { method: 'GET' })
        .then(response => response.json())
        .then(messages => {
            const messagesContainer = document.getElementById('condolencesMessages');
            messagesContainer.innerHTML = ''; // Limpar mensagens anteriores

            if (messages.length === 0) {
                messagesContainer.innerHTML = '<p>Nenhuma mensagem encontrada.</p>';
            } else {
                messages.forEach(message => {
                    const messageElement = document.createElement('div');
                    messageElement.className = 'message';
                    messageElement.innerHTML = `
                        <p><strong>${message.nome_remetente}:</strong> ${message.mensagem}</p>
                        <small>Enviado em: ${new Date(message.data_envio).toLocaleDateString('pt-BR')}</small>
                    `;
                    messagesContainer.appendChild(messageElement);
                });
            }
        })
        .catch(error => {
            console.error('Erro ao carregar mensagens:', error);
            alert('Erro ao carregar as mensagens. Tente novamente mais tarde.');
        });
}


document.addEventListener('DOMContentLoaded', () => {
    const currentPage = window.location.pathname.split('/').pop();

    // Verificar a página atual e executar o script necessário
    if (currentPage === 'index.html') {
        console.log('Página inicial carregada.');
    } else if (currentPage === 'search.html') {
        displayResults();
    } else if (currentPage === 'deceased-info.html') {
        displayDeceasedInfo();
    } else if (currentPage === 'cemetery-info.html') {
        displayCemeteryInfo();
    } else if (currentPage === 'more-info.html') {
        displayMoreInfo();
    } else if (currentPage === 'condolences.html') {
        displayDeceasedInCondolencePage();  // Exibir informações do falecido
        setupCondolenceForm();  // Configurar o envio de mensagem de condolência
    } else if (currentPage === 'messages.html') {
        loadRecentMessages();  // Carregar últimas mensagens
    }
});