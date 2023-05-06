getAllLivros();

function getAllLivros () {
    // Parametros default do getAll
    const queryGetAll = [
        {name: 'page', value: 0},
        {name: 'linesPerPage', value: 10},
        {name: 'direction', value: 'DESC'},
        {name: 'orderBy', value: 'id'}
    ];
    const mapOrder = [
        'id',
        'nome',
        'nomeFantasia'   
    ];
    // Inicializa e configura a tabela
    window['dtAutores'] = $('#tb_autores').DataTable({
        initComplete: function () {
            iniciarComportamentos();
        },
        destroy       : true,
        processing    : true,
        serverSide    : true,
        scrollX       : true,
        autoWidth     : true,
        scrollY       : '55vh',
        scrollCollapse: true,
        responsive    : true,
        paging        : true,
        ordering      : true,
        searching     : false,
        columns: [
            {data: 'id'},
            {data: 'nome'},
            {data: 'nomeFantasia'}
        ],
        columnDefs: [
            {
                targets: [0],
                type: 'num'
            }
        ],
        ajaxSource: rotas.autores,
        serverData: function (sSource, aoData, fnCallback) {
            console.log(aoData)

            if (typeof window['dtAutores'] !== 'undefined') {
                queryGetAll[0].value = window['dtAutores'].page();
                queryGetAll[1].value = window['dtAutores'].page.len();
                
                const ordenacaoAtual = window['dtAutores'].order();
                console.log(ordenacaoAtual);
                switch (ordenacaoAtual[0][0]) {
                    case 0:
                        queryGetAll[3].value = mapOrder[0];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                    case 1:
                        queryGetAll[3].value = mapOrder[1];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                    case 2:
                        queryGetAll[3].value = mapOrder[2];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                }
            }

            queryGetAll.map(element => aoData.push(element));
            console.log(queryGetAll);
            $.ajax({
                type: 'GET',
                url : sSource,
                data: aoData,
                success: function (sSource, aoData) {
                    console.log(sSource);
                    // Transforma os IDs em ancoras
                    sSource.content.map(element => {
                        element.id = `<a class="text-decoration-none" name="atualizar_cadastro" href="${element.id}"><i class="fa-regular fa-hand-point-right"></i> ${element.id}</a>`;
                    });

                    // Ajusta as propriedades para renderizar a tabela
                    sSource.iTotalDisplayRecords = sSource.totalElements;
                    sSource.iTotalRecords        = sSource.totalElements;
                    sSource.aaData               = sSource.content;
                    fnCallback(sSource, aoData);
                },
                error: function (response, status) {
                    console.log(response);
                }
            });
        }
    });
}

function getAutor(id) {
    $.ajax({
        type   : 'GET',
        url    : `${rotas.autores}/${id}`,
        success: function (response, status) {
            console.log(response);

            $('#nome_autor').val(response.nome);
            $('#nome_fantasia').val(response.nomeFantasia);
            $('#template_autor #limpar, #salvar').data('id', response.id);
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}


function iniciarComportamentos() {
    /* Eventos */
    // Template - Adicionar
    $('#listagem_autores').on('click', '#adicionar', function () {
        gerenciarFormAutor();
        $('#nome_autor').focus();
        return false;
    });
    // Template - Atualizar
    $('#tb_autores tbody').on('click', 'td a[name="atualizar_cadastro"]', function () {
        getAutor($(this).attr('href'));
        gerenciarFormAutor('atualizar');
        return false;
    });
    // Salvar
    $('#template_autor').on('click', '#salvar', function () {
        $(this).submit();
        return false;
    });
    // Voltar para a lista
    $('#template_autor').on('click', '#voltar', function () {
        $('#listagem_autores, #template_autor').toggleClass('d-none');
        resetarFormulario(document.getElementById('form_autor'));
        return false;
    });
    // Comportamento do botão limpar/excluir
    $('#form_autor').on('click', '#limpar', function () {
        switch ($(this).data('modo')) {
            case 'limpar':
                resetarFormulario(this.form);
                break;
            case 'excluir':
                deleteAutor()
                break;
            default:
                alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
        }
        return false;
    });
}

function gerenciarFormAutor (acao = 'adicionar') {
    // Alterna a exibição
    $('#listagem_autores, #template_autor').toggleClass('d-none');
    // Define o tipo de operação
    switch (acao) {
        case 'adicionar':
                addAutor();
            break;
        case 'atualizar':
                updateAutor();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
    }
}

function addAutor() {
    // Exibição
    $('#template_autor > header > label').text('Adicionar Autor');
    $('#template_autor #salvar').text('Salvar');
    
    // Gerencia atributos dos botões
    $('#template_autor #limpar').text('Limpar').data('modo', 'limpar');
    
    if ($('#template_autor #salvar').data('id')) {
        $('#template_autor #salvar').removeData('id');
    }
    
    if ($('#template_autor #limpar').data('id')) {
        $('#template_autor #limpar').removeData('id');
    }
    
    // Inicia e configura validador do formulário
    $('#template_autor #form_autor').validate({
        debug: false,
        rules: {
            nome_autor: {
                required: true
            },
            nome_fantasia: {
                required: true
            }
        },
        messages: {
            nome_autor: msg_erro_geral,
            nome_fantasia: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_autor form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                nome    : $('#nome_autor').val(),
                nomeFantasia : $('#nome_fantasia').val()
            }
            console.log(dataVar);
            // return false; 
            $.ajax({
                type       : 'POST',
                cache      : false,
                url        : rotas.autores,
                // dataType   : 'json',
                contentType: 'application/json; charset=UTF-8',
                data       : JSON.stringify(dataVar),
                beforeSend : function () {
                    console.log('Enviando requisição');
                },
                complete: function () {
                    console.log('Envio concluído');
                },
                success: function (response, status) {
                    console.log(response);
                    
                    window['dtAutores'].ajax.reload();
                    $('#form_autor footer #voltar').click();
                    resetarFormulario(document.getElementById('form_autor'));
                },
                error: function (response, status) {
                    console.log(response);
                }
            });

            return false;
        }
    });
}

function updateAutor() {
    $('#template_autor > header > label').text('Atualizar Livro');
    $('#template_autor #salvar').text('Atualizar');
    // Gerencia atributos do botão de limpar/excluir
    $('#template_autor #limpar').text('Excluir').data('modo', 'excluir');

    // Inicia e configura validador do formulário
    $('#template_autor #form_autor').validate({
        debug: false,
        rules: {
            nome_autor: {
                required: true
            },
            nome_fantasia: {
                required: true
            }
        },
        messages: {
            nome_autor: msg_erro_geral,
            nome_fantasia: msg_erro_geral,
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_autor form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                nome    : $('#nome_autor').val(),
                nomeFantasia : $('#nome_fantasia').val(),
            }
            console.log(dataVar);
            // return false
            $.ajax({
                type       : 'PUT',
                cache      : false,
                url        : `${rotas.autores}/${$('#template_autor #salvar').data('id')}`,
                contentType: 'application/json; charset=UTF-8',
                data       : JSON.stringify(dataVar),
                beforeSend : function () {
                    console.log('Enviando requisição');
                },
                complete: function () {
                    console.log('Envio concluído');
                },
                success: function (response, status) {
                    console.log('deu certo ')
                    console.log(response);
                    
                    window['dtAutores'].ajax.reload();
                    $('#form_autor footer #voltar').click();
                    resetarFormulario(document.getElementById('form_autor'));
                },
                error: function (response, status) {
                    console.log(response);
                }
            });

            return false;
        }
    });
}

function deleteAutor() {
    $.ajax({
        type   : 'DELETE',
        url    : `${rotas.autores}/${$('#template_autor #limpar').data('id')}`,
        success: function (response, status) {
            window['dtAutores'].ajax.reload();
            $('#form_autor footer #voltar').click();
            resetarFormulario(document.getElementById('form_autor'));
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}
