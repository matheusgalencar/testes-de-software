import { test, expect } from '@playwright/test';
const casos = [
  {
    "descricao": "aceita senha no limite mínimo de oito caracteres",
    "senha": "Aa1aaaaa",
    "confirmacao": "Aa1aaaaa",
    "esperado": "Senha cadastrada",
    "valido": true
  },
  {
    "descricao": "aceita senha com nove caracteres",
    "senha": "Aa1aaaaaa",
    "confirmacao": "Aa1aaaaaa",
    "esperado": "Senha cadastrada",
    "valido": true
  },
  {
    "descricao": "aceita senha no limite máximo de vinte caracteres",
    "senha": "Aa1aaaaaaaaaaaaaaaa",
    "confirmacao": "Aa1aaaaaaaaaaaaaaaa",
    "esperado": "Senha cadastrada",
    "valido": true
  },
  {
    "descricao": "aceita senha com vinte e um caracteres quando respeita as regras da página",
    "senha": "Aa1aaaaaaaaaaaaaaaaa",
    "confirmacao": "Aa1aaaaaaaaaaaaaaaaa",
    "esperado": "Senha cadastrada",
    "valido": true
  },
  {
    "descricao": "aceita senha com caractere especial",
    "senha": "Aa1!aaaa",
    "confirmacao": "Aa1!aaaa",
    "esperado": "Senha cadastrada",
    "valido": true
  },
  {
    "descricao": "rejeita senha com sete caracteres",
    "senha": "Aa1aaaa",
    "confirmacao": "Aa1aaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha acima do limite máximo",
    "senha": "Aa1aaaaaaaaaaaaaaaaaa",
    "confirmacao": "Aa1aaaaaaaaaaaaaaaaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha sem letra maiúscula",
    "senha": "aa1aaaaa",
    "confirmacao": "aa1aaaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha sem letra minúscula",
    "senha": "AA1AAAAA",
    "confirmacao": "AA1AAAAA",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha sem número",
    "senha": "Aaaaaaaa",
    "confirmacao": "Aaaaaaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha com espaço no meio",
    "senha": "Aa1 aaaa",
    "confirmacao": "Aa1 aaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha com espaço no início",
    "senha": " Aa1aaaaa",
    "confirmacao": " Aa1aaaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha com espaço no fim",
    "senha": "Aa1aaaaa ",
    "confirmacao": "Aa1aaaaa ",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha vazia",
    "senha": "",
    "confirmacao": "",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita senha com tabulação",
    "senha": "Aa1\taaaa",
    "confirmacao": "Aa1\taaaa",
    "esperado": "Senha fora do padrão",
    "valido": false
  },
  {
    "descricao": "rejeita confirmação diferente",
    "senha": "Aa1aaaaa",
    "confirmacao": "Aa1aaaab",
    "esperado": "As senhas não coincidem",
    "valido": false
  },
  {
    "descricao": "rejeita confirmação vazia",
    "senha": "Aa1aaaaa",
    "confirmacao": "",
    "esperado": "As senhas não coincidem",
    "valido": false
  },
  {
    "descricao": "rejeita confirmação com diferença entre maiúscula e minúscula",
    "senha": "Aa1aaaaa",
    "confirmacao": "aa1aaaaa",
    "esperado": "As senhas não coincidem",
    "valido": false
  }
];
for (const c of casos) {
  test(c.descricao, async ({ page }) => {
    await page.goto('/senha');
    await page.getByLabel('Nova senha').fill(c.senha);
    await page.getByLabel('Confirmar senha').fill(c.confirmacao);
    await page.getByRole('button', { name: 'Cadastrar senha' }).click();
    await expect(page.getByRole(c.valido ? 'status' : 'alert')).toHaveText(c.esperado);
    if (c.valido) {
      await expect(page.getByLabel('Nova senha')).toHaveValue('');
      await expect(page.getByLabel('Confirmar senha')).toHaveValue('');
    } else {
      await expect(page.getByRole('status')).toHaveCount(0);
    }
  });
}
test('valida o formato antes da confirmação e permite corrigir os dados', async ({ page }) => {
  await page.goto('/senha');
  await page.getByLabel('Nova senha').fill('curta');
  await page.getByLabel('Confirmar senha').fill('diferente');
  await page.getByRole('button', { name: 'Cadastrar senha' }).click();
  await expect(page.getByRole('alert')).toHaveText('Senha fora do padrão');
  await page.getByLabel('Nova senha').fill('Aa1aaaaa');
  await page.getByRole('button', { name: 'Cadastrar senha' }).click();
  await expect(page.getByRole('alert')).toHaveText('As senhas não coincidem');
  await page.getByLabel('Confirmar senha').fill('Aa1aaaaa');
  await page.getByRole('button', { name: 'Cadastrar senha' }).click();
  await expect(page.getByRole('status')).toHaveText('Senha cadastrada');
});
