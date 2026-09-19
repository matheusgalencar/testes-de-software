import { test, expect } from '@playwright/test';
const casos = [
  {
    "cep": "80000000",
    "valor": "199,99",
    "esperado": "Frete: R$ 15,00",
    "valido": true
  },
  {
    "cep": "01000000",
    "valor": "199.99",
    "esperado": "Frete: R$ 25,00",
    "valido": true
  },
  {
    "cep": "80000000",
    "valor": "200",
    "esperado": "Frete grátis",
    "valido": true
  },
  {
    "cep": "01000000",
    "valor": "200,00",
    "esperado": "Frete grátis",
    "valido": true
  },
  {
    "cep": "80000000",
    "valor": "200.01",
    "esperado": "Frete grátis",
    "valido": true
  },
  {
    "cep": "01000000",
    "valor": "200,01",
    "esperado": "Frete grátis",
    "valido": true
  },
  {
    "cep": "80000000",
    "valor": "0.01",
    "esperado": "Frete: R$ 15,00",
    "valido": true
  },
  {
    "cep": "01000000",
    "valor": "0,01",
    "esperado": "Frete: R$ 25,00",
    "valido": true
  },
  {
    "cep": " 80000000 ",
    "valor": " 100,50 ",
    "esperado": "Frete: R$ 15,00",
    "valido": true
  },
  {
    "cep": "",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "8000000",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "800000000",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "abcdefgh",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000-000",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "8000 000",
    "valor": "100",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "0",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "-0.01",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "abc",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "100.001",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "1e2",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "1.000,00",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "8000000",
    "valor": "200",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": "Infinity",
    "esperado": "Dados inválidos",
    "valido": false
  },
  {
    "cep": "80000000",
    "valor": " ",
    "esperado": "Dados inválidos",
    "valido": false
  }
];
for (const c of casos) {
  test(`${c.valido ? 'aceita' : 'rejeita'} código postal "${c.cep}" e valor "${c.valor}"`, async ({ page }) => {
    await page.goto('/frete');
    await page.getByLabel('CEP', { exact: true }).fill(c.cep);
    await page.getByLabel('Valor do pedido').fill(c.valor);
    await page.getByRole('button', { name: 'Calcular frete' }).click();
    await expect(page.getByRole(c.valido ? 'status' : 'alert')).toHaveText(c.esperado);
    await expect(page.getByRole(c.valido ? 'alert' : 'status')).toHaveCount(0);
  });
}
test('corrige os dados depois de um erro e calcula novamente', async ({ page }) => {
  await page.goto('/frete');
  await page.getByRole('button', { name: 'Calcular frete' }).click();
  await expect(page.getByRole('alert')).toHaveText('Dados inválidos');
  await page.getByLabel('CEP', { exact: true }).fill('80000000');
  await page.getByLabel('Valor do pedido').fill('100');
  await page.getByRole('button', { name: 'Calcular frete' }).click();
  await expect(page.getByRole('status')).toHaveText('Frete: R$ 15,00');
  await page.getByLabel('Valor do pedido').fill('200');
  await page.getByRole('button', { name: 'Calcular frete' }).click();
  await expect(page.getByRole('status')).toHaveText('Frete grátis');
});

