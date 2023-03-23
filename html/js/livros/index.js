// livros_get_all();

dtListagemLivros([]);
/**
 * tabela para debug
 */
function dtListagemLivros (linhas) {
    // Inicializa e configura a tabela
    window['dtLivros'] = $('#tb_livros').DataTable({
        initComplete: function () {
            // Template - Adicionar
            $('#listagem_livros').on('click', '#adicionar', function () {
                gerenciar_template_livro();
                return false;
            });
            // Template - Atualizar
            $('#tb_livros tbbody td').on('click', 'a[name="atualizar_cadastro"]', function () {
                gerenciar_template_livro('atualizar');
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
                return false;
            });
        },
        scrollX       : true,
        autoWidth     : true,
        scrollY       : '55vh',
        scrollCollapse: true,
        paging        : true,
        destroy       : true,
        columnDefs    : [
            {
                targets: [0,5],
                type: 'num'
            }
        ],
        ordering: true,
        order: [0, 'desc']
    });
    // Percorre e adiciona as linhas virtualmente
    if (linhas.length) {
        for (i = 0; i < linhas.length; i++) {
            window['dtLivros'].row.add([
                linhas[i].id,
                linhas[i].titulo,
                linhas[i].subtitulo,
                linhas[i].autor,
                linhas[i].editora,
                linhas[i].unidades
            ]);
        }
    }
    // Desenha as linhas
    window['dtLivros'].draw();
}

function gerenciar_template_livro (acao = 'adicionar') {
    // Alterna a exibição
    $('#listagem_livros, #template_livro').toggleClass('d-none');
    // Define o tipo de operação
    switch (acao) {
        case 'adicionar':
                add_livro();
            break;
        case 'atualizar':
                get_livro();
                update_livro();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
            return false;
    }
    // Configuração de máscara
    $('#livro_isbn').mask(MaskISBN, MaskISBNOptions);
}

function add_livro() {
    // Exibição
    $('#template_livro > header > label').text('Adicionar Livro');
    // Adiciona validação de ISBN
    $.validator.addMethod('validaISBN', function (value, element) {
        // Checa o tamanho do input sem mascara
        if ($(element).cleanVal().length === 10 || $(element).cleanVal().length === 13) {
            return true;
        }
        return false;
    }, 'ISBN inválido!');
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
            livro_titulo: msg_erro_geral,
            livro_subtitulo: msg_erro_geral,
            livro_autores: msg_erro_geral,
            livro_generos: msg_erro_geral,
            livro_editora: msg_erro_geral,
            livro_paginas: msg_erro_geral,
            livro_isbn: {
                required: msg_erro_geral
            },
            livro_unidades: msg_erro_geral,
            livro_descricao: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            error.insertAfter(element);
            $('#template_livro form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('enviando ajax')
            //todo: continuar a partir daqui

            return false;
        }
    });
}

function get_livro() {
    
}

function update_livro() {
    $('#template_livro > header > label').text('Atualizar Livro');
    $('#template_livro #salvar').text('Atualizar');
    
}
