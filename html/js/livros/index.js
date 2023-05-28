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
        'titulo',
        'subtitulo',
        'autor',
        'editora',
        'unidades'
    ];
    // Inicializa e configura a tabela
    window['dtLivros'] = $('#tb_livros').DataTable({
        initComplete: function () {
            getOptionsEditoras();
            getOptionsAutores();
            getOptionsGeneros();
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
            {data: 'titulo'},
            {data: 'subtitulo'},
            {data: 'autores.0.nomeFantasia'},
            {data: 'editora.nomeFantasia'},
            {data: 'quantidade'}
        ],
        columnDefs: [
            {
                targets: [0,5],
                type: 'num'
            }
        ],
        ajaxSource: rotas.livros,
        serverData: function (sSource, aoData, fnCallback) {
            console.log(aoData)

            if (typeof window['dtLivros'] !== 'undefined') {
                queryGetAll[0].value = window['dtLivros'].page();
                queryGetAll[1].value = window['dtLivros'].page.len();
                
                const ordenacaoAtual = window['dtLivros'].order();
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

function getLivro(id) {
    $.ajax({
        type   : 'GET',
        url    : `${rotas.livros}/${id}`,
        success: function (response, status) {
            console.log(response);

            $('#livro_titulo').val(response.titulo);
            $('#livro_subtitulo').val(response.subtitulo);
            $('#livro_descricao').val(response.descricao);
            $('#livro_paginas').val(response.paginas);
            $('#livro_isbn').val(response.isbn);
            $('#livro_unidades').val(response.quantidade);
            $('#livro_editora').val(response.editora.id).trigger('change');
            $('#livro_autores').val(response.autores.map(index => index.id)).trigger('change');
            $('#livro_generos').val(response.generosLiterarios.map(index => index.id)).trigger('change');
            $('#template_livro #limpar, #salvar').data('id', response.id);
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function getOptionsEditoras() {
    $.ajax({
        type   : 'GET',
        url    : rotas.editoras,
        success: function (response, status) {
            // console.log(response);
            if (response?.content?.length) {
                $('#livro_editora').append(
                    response.content.map(element => new Option(element.nomeFantasia, element.id, false, false))
                ).val(null).trigger('change');
            }
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function getOptionsAutores() {
    $.ajax({
        type   : 'GET',
        url    : rotas.autores,
        success: function (response, status) {
            // console.log(response);
            if (response?.content?.length) {
                $('#livro_autores').append(
                    response.content.map(element => new Option(element.nomeFantasia, element.id, false, false))
                ).val(null).trigger('change');
            }
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function getOptionsGeneros() {
    $.ajax({
        type   : 'GET',
        url    : rotas.generos_literarios,
        success: function (response, status) {
            // console.log(response);
            if (response?.content?.length) {
                $('#livro_generos').append(
                    response.content.map(element => new Option(element.descricao, element.id, false, false))
                ).val(null).trigger('change');
            }
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function iniciarComportamentos() {
    /* Eventos */
    // Template - Adicionar
    $('#listagem_livros').on('click', '#adicionar', function () {
        gerenciarFormLivro();
        $('#livro_titulo').focus();
        return false;
    });
    // Template - Atualizar
    $('#tb_livros tbody').on('click', 'td a[name="atualizar_cadastro"]', function () {
        getLivro($(this).attr('href'));
        gerenciarFormLivro('atualizar');
        return false;
    });
    // Salvar
    $('#template_livro').on('click', '#salvar', function () {
        $(this).submit();
        return false;
    });
    // Voltar para a lista
    $('#template_livro').on('click', '#voltar', function () {
        $('#listagem_livros, #template_livro').toggleClass('d-none');
        resetarFormulario(document.getElementById('form_livro'));
        return false;
    });
    // Comportamento do botão limpar/excluir
    $('#form_livro').on('click', '#limpar', function () {
        switch ($(this).data('modo')) {
            case 'limpar':
                resetarFormulario(this.form);
                break;
            case 'excluir':
                deleteLivro()
                break;
            default:
                alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
        }
        return false;
    });
    /* Configurações gerais */
    // Máscara ISBN
    $('#livro_isbn').mask(MaskISBN, MaskISBNOptions);
    // Select inteligente
    $('#form_livro #livro_editora').select2({
        placeholder: 'Selecione',
        width      : '100%',
        allowClear : true
    });
    $('#form_livro #livro_autores, #livro_generos').select2({
        placeholder: 'Selecione',
        width      : '100%',
        allowClear : true,
        multiple   : true
    });
}

function gerenciarFormLivro (acao = 'adicionar') {
    // Alterna a exibição
    $('#listagem_livros, #template_livro').toggleClass('d-none');
    // Define o tipo de operação
    switch (acao) {
        case 'adicionar':
                addLivro();
            break;
        case 'atualizar':
                updateLivro();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
    }
}

function addLivro() {
    // Exibição
    $('#template_livro > header > label').text('Adicionar Livro');
    $('#template_livro #salvar').text('Salvar');
    
    // Gerencia atributos dos botões
    $('#template_livro #limpar').text('Limpar').data('modo', 'limpar');
    
    if ($('#template_livro #salvar').data('id')) {
        $('#template_livro #salvar').removeData('id');
    }
    
    if ($('#template_livro #limpar').data('id')) {
        $('#template_livro #limpar').removeData('id');
    }

    $('#livro_isbn, #livro_autores, #livro_generos').prop('disabled', false);
    
    // Inicia e configura validador do formulário
    $('#template_livro #form_livro').validate({
        debug: false,
        rules: {
            livro_titulo: {
                required: true
            },
            livro_subtitulo: {
                required: true
            },
            livro_autores: {
                required: true
            },
            livro_generos: {
                required: true
            },
            livro_editora: {
                required: true
            },
            livro_paginas: {
                required: true
            },
            livro_isbn: {
                required: true,
                validaISBN: true
            },
            livro_unidades: {
                required: true
            },
            livro_descricao: {
                required: true
            }
        },
        messages: {
            livro_titulo   : msg_erro_geral,
            livro_subtitulo: msg_erro_geral,
            livro_autores  : msg_erro_geral,
            livro_generos  : msg_erro_geral,
            livro_editora  : msg_erro_geral,
            livro_paginas  : msg_erro_geral,
            livro_isbn     : {
                required: msg_erro_geral
            },
            livro_unidades : msg_erro_geral,
            livro_descricao: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_livro form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                titulo             : $('#livro_titulo').val(),
                subtitulo          : $('#livro_subtitulo').val(),
                descricao          : $('#livro_descricao').val(),
                paginas            : $('#livro_paginas').val(),
                isbn               : $('#livro_isbn').val(),
                quantidade         : $('#livro_unidades').val(),
                editoraId          : $('#livro_editora').val(),
                autoresId          : $('#livro_autores').val(),
                generosLiterariosId: $('#livro_generos').val()
            }
            console.log(dataVar);
            $.ajax({
                type       : 'POST',
                cache      : false,
                url        : rotas.livros,
                dataType   : 'json',
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
                    
                    window['dtLivros'].ajax.reload();
                    $('#form_livro footer #voltar').click();
                    resetarFormulario(document.getElementById('form_livro'));
                },
                error: function (response, status) {
                    console.log(response);
                    console.log(status);
                    console.log(response?.responseJSON?.error);
                    console.log(response?.responseJSON?.message);
                    console.log(response?.responseJSON?.status);
                }
            });

            return false;
        }
    });
}

function updateLivro() {
    $('#template_livro > header > label').text('Atualizar Livro');
    $('#template_livro #salvar').text('Atualizar');
    // Gerencia atributos do botão de limpar/excluir
    $('#template_livro #limpar').text('Excluir').data('modo', 'excluir');
    $('#livro_isbn, #livro_autores, #livro_generos').prop('disabled', true);

    // Inicia e configura validador do formulário
    $('#template_livro #form_livro').validate({
        debug: false,
        rules: {
            livro_titulo: {
                required: true
            },
            livro_subtitulo: {
                required: true
            },
            livro_autores: {
                required: true
            },
            livro_generos: {
                required: true
            },
            livro_editora: {
                required: true
            },
            livro_paginas: {
                required: true
            },
            livro_isbn: {
                required: true,
                validaISBN: true
            },
            livro_unidades: {
                required: true
            },
            livro_descricao: {
                required: true
            }
        },
        messages: {
            livro_titulo   : msg_erro_geral,
            livro_subtitulo: msg_erro_geral,
            livro_autores  : msg_erro_geral,
            livro_generos  : msg_erro_geral,
            livro_editora  : msg_erro_geral,
            livro_paginas  : msg_erro_geral,
            livro_isbn     : {
                required: msg_erro_geral
            },
            livro_unidades : msg_erro_geral,
            livro_descricao: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_livro form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                titulo             : $('#livro_titulo').val(),
                subtitulo          : $('#livro_subtitulo').val(),
                descricao          : $('#livro_descricao').val(),
                paginas            : $('#livro_paginas').val(),
                isbn               : $('#livro_isbn').val(),
                quantidade         : $('#livro_unidades').val(),
                editoraId          : $('#livro_editora').val(),
                autoresId          : $('#livro_autores').val(),
                generosLiterariosId: $('#livro_generos').val()
            }
            console.log(dataVar);
            $.ajax({
                type       : 'PUT',
                cache      : false,
                url        : `${rotas.livros}/${$('#template_livro #salvar').data('id')}`,
                dataType   : 'json',
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
                    
                    window['dtLivros'].ajax.reload();
                    $('#form_livro footer #voltar').click();
                    resetarFormulario(document.getElementById('form_livro'));
                },
                error: function (response, status) {
                    console.log(response);
                    console.log(status);
                    console.log(response?.responseJSON?.error);
                    console.log(response?.responseJSON?.message);
                    console.log(response?.responseJSON?.status);
                }
            });

            return false;
        }
    });
}

function deleteLivro() {
    $.ajax({
        type   : 'DELETE',
        url    : `${rotas.livros}/${$('#template_livro #limpar').data('id')}`,
        success: function (response, status) {
            window['dtLivros'].ajax.reload();
            $('#form_livro footer #voltar').click();
            resetarFormulario(document.getElementById('form_livro'));
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}
