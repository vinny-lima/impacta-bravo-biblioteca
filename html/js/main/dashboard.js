$('#sidebar-nav').on('click', 'a', function () {
    if (!$(this).hasClass('active')) {
        // Alterna o estado ativo entre os itens do menu
        $(this).siblings('.active').toggleClass('active text-light text-white-50');
        $(this).toggleClass('active text-light text-white-50');
        // Carrega o conteudo principal dos módulos com base no href
        $('#app').load(`views${$(this).attr('href')}/index.html`);
    }
    
    return false;
});

$('#app').load(`views/home/index.html`);
$('footer span').append(new Date().getFullYear());
