const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');

const port = Number(process.env.PORT || 3000);
const publicDir = path.join(__dirname, 'public');
const pages = {
  "/": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Login didático</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Acessar minha conta</h1>\n    <p class=\"hint\">Conta válida: ana@exemplo.com / SenhaSegura123!</p>\n    <form id=\"login-form\" novalidate>\n      <label for=\"email\">E-mail</label>\n      <input id=\"email\" name=\"email\" type=\"email\" autocomplete=\"username\">\n      <label for=\"senha\">Senha</label>\n      <input id=\"senha\" name=\"senha\" type=\"password\" autocomplete=\"current-password\">\n      <button type=\"submit\">Entrar</button>\n      <p id=\"mensagem\" role=\"alert\" hidden></p>\n    </form>\n    <nav>\n      <a href=\"/idade\">Testar idade</a>\n      <a href=\"/frete\">Atividade: frete</a>\n      <a href=\"/senha\">Atividade: senha</a>\n    </nav>\n  </main>\n  <script src=\"/login.js\"></script>\n</body>\n</html>\n",
  "/login": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Login didático</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Acessar minha conta</h1>\n    <p class=\"hint\">Conta válida: ana@exemplo.com / SenhaSegura123!</p>\n    <form id=\"login-form\" novalidate>\n      <label for=\"email\">E-mail</label>\n      <input id=\"email\" name=\"email\" type=\"email\" autocomplete=\"username\">\n      <label for=\"senha\">Senha</label>\n      <input id=\"senha\" name=\"senha\" type=\"password\" autocomplete=\"current-password\">\n      <button type=\"submit\">Entrar</button>\n      <p id=\"mensagem\" role=\"alert\" hidden></p>\n    </form>\n    <nav>\n      <a href=\"/idade\">Testar idade</a>\n      <a href=\"/frete\">Atividade: frete</a>\n      <a href=\"/senha\">Atividade: senha</a>\n    </nav>\n  </main>\n  <script src=\"/login.js\"></script>\n</body>\n</html>\n",
  "/conta": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Minha conta</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Minha conta</h1>\n    <p class=\"success\">Login realizado com sucesso.</p>\n    <p data-testid=\"usuario\">Usuário: Ana</p>\n    <a class=\"button\" href=\"/login\">Sair</a>\n  </main>\n</body>\n</html>\n\n",
  "/idade": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Validação de idade</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Cadastro de paciente</h1>\n    <p>A idade deve ser um número inteiro de 18 a 65, inclusive.</p>\n    <form id=\"idade-form\" novalidate>\n      <label for=\"idade\">Idade</label>\n      <input id=\"idade\" name=\"idade\" inputmode=\"numeric\">\n      <button type=\"submit\">Validar cadastro</button>\n      <p id=\"resultado\" hidden></p>\n    </form>\n    <nav><a href=\"/login\">Voltar ao login</a></nav>\n  </main>\n  <script src=\"/idade.js\"></script>\n</body>\n</html>\n\n",
  "/frete": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Calculadora de frete</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Calcular frete</h1>\n    <p>Informe um CEP com 8 dígitos e o valor do pedido.</p>\n    <ul class=\"rules\">\n      <li>CEP iniciado por 8: frete de R$ 15,00.</li>\n      <li>Demais CEPs: frete de R$ 25,00.</li>\n      <li>Pedidos a partir de R$ 200,00 têm frete grátis.</li>\n    </ul>\n    <form id=\"frete-form\" novalidate>\n      <label for=\"cep\">CEP</label>\n      <input id=\"cep\" name=\"cep\" inputmode=\"numeric\" placeholder=\"00000000\">\n      <label for=\"valor\">Valor do pedido</label>\n      <input id=\"valor\" name=\"valor\" inputmode=\"decimal\" placeholder=\"0,00\">\n      <button type=\"submit\">Calcular frete</button>\n      <p id=\"resultado\" hidden></p>\n    </form>\n    <nav><a href=\"/login\">Voltar ao início</a></nav>\n  </main>\n  <script src=\"/frete.js\"></script>\n</body>\n</html>\n",
  "/senha": "<!doctype html>\n<html lang=\"pt-BR\">\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Cadastro de senha</title>\n  <link rel=\"stylesheet\" href=\"/styles.css\">\n</head>\n<body>\n  <main>\n    <h1>Criar senha</h1>\n    <p>A senha deve ter de 8 a 20 caracteres, ao menos uma letra maiúscula, uma minúscula e um número. Espaços não são permitidos.</p>\n    <form id=\"senha-form\" novalidate>\n      <label for=\"senha\">Nova senha</label>\n      <input id=\"senha\" name=\"senha\" type=\"password\" autocomplete=\"new-password\">\n      <label for=\"confirmacao\">Confirmar senha</label>\n      <input id=\"confirmacao\" name=\"confirmacao\" type=\"password\" autocomplete=\"new-password\">\n      <button type=\"submit\">Cadastrar senha</button>\n      <p id=\"resultado\" hidden></p>\n    </form>\n    <nav><a href=\"/login\">Voltar ao início</a></nav>\n  </main>\n  <script src=\"/senha.js\"></script>\n</body>\n</html>\n"
};
const contentTypes = {
  '.css': 'text/css; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
};

const server = http.createServer((request, response) => {
  const url = new URL(request.url, `http://${request.headers.host}`);

  if (Object.hasOwn(pages, url.pathname)) {
    response.writeHead(200, {
      'Content-Type': 'text/html; charset=utf-8',
      'Cache-Control': 'no-store',
    });
    response.end(pages[url.pathname]);
    return;
  }

  const requested = url.pathname.replace(/^\//, '');
  const file = path.normalize(path.join(publicDir, requested));
  if (!file.startsWith(publicDir)) {
    response.writeHead(403);
    response.end('Acesso negado');
    return;
  }

  fs.readFile(file, (error, content) => {
    if (error) {
      response.writeHead(error.code === 'ENOENT' ? 404 : 500);
      response.end(error.code === 'ENOENT' ? 'Página não encontrada' : 'Erro interno');
      return;
    }
    response.writeHead(200, {
      'Content-Type': contentTypes[path.extname(file)] || 'application/octet-stream',
      'Cache-Control': 'no-store',
    });
    response.end(content);
  });
});

server.listen(port, '127.0.0.1', () => {
  console.log(`Aplicação didática disponível em http://127.0.0.1:${port}`);
});
