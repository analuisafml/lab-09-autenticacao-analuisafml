# API Test Script - Figurinhas Copa

Este arquivo contém um script bash abrangente para testar todas as rotas da API da aplicação Figurinhas Copa usando CURL.

## 📋 Pré-requisitos

1. **Aplicação rodando**: Certifique-se de que a aplicação Spring Boot está executando em `http://localhost:8080`
2. **CURL instalado**: O script usa CURL para fazer as requisições HTTP
3. **Bash**: Script compatível com bash/sh

## 🚀 Como Executar

```bash
# Tornar o script executável (já feito)
chmod +x test_api.sh

# Executar os testes
./test_api.sh
```

## 📊 O que o Script Testa

### 🧑‍💼 **User Endpoints (9 testes)**
- ✅ Criar usuário (sucesso e erro)
- ✅ Listar todos os usuários
- ✅ Buscar usuário por ID (sucesso e não encontrado)
- ✅ Atualizar usuário (sucesso e não encontrado)
- ✅ Atualizar foto do usuário (sucesso e não encontrado)

### 🎴 **Figurinha Endpoints (9 testes)**
- ✅ Criar figurinha (sucesso e erro)
- ✅ Listar todas as figurinhas
- ✅ Buscar figurinha por ID (sucesso e não encontrado)
- ✅ Buscar figurinhas por seleção
- ✅ Atualizar figurinha (sucesso e não encontrado)

### 📚 **Album Endpoints (9 testes)**
- ✅ Criar álbum (sucesso, usuário não encontrado, dados vazios)
- ✅ Listar todos os álbuns
- ✅ Buscar álbum por ID (sucesso e não encontrado)
- ✅ Buscar álbuns por usuário
- ✅ Atualizar nome do álbum (sucesso e não encontrado)

### 🔗 **Album-Figurinha Relationships (8 testes)**
- ✅ Adicionar figurinha ao álbum (sucesso e erro)
- ✅ Adicionar figurinha duplicada (erro)
- ✅ Adicionar figurinha a álbum inexistente (erro)
- ✅ Adicionar figurinha inexistente (erro)
- ✅ Remover figurinha do álbum (sucesso e erro)
- ✅ Remover figurinha de álbum inexistente (erro)

### 🗑️ **Delete Operations (6 testes)**
- ✅ Deletar álbum (sucesso e não encontrado)
- ✅ Deletar figurinha (sucesso e não encontrado)
- ✅ Deletar usuário (sucesso e não encontrado)

## 📈 **Total: 41 Testes**

## 🎨 Saída do Script

O script produz uma saída colorida com:
- 🟢 **Verde**: Testes que passaram
- 🔴 **Vermelho**: Testes que falharam
- 🔵 **Azul**: Cabeçalhos de seção
- 🟡 **Amarelo**: Informações gerais

## 📝 Exemplo de Saída

```bash
Starting API Tests for Figurinhas Copa Application
Make sure the application is running on http://localhost:8080/api

========================================
TESTING USER ENDPOINTS
========================================

✓ PASS - Create User - Success (Status: 201)
✗ FAIL - Create User - Empty Data (Expected: 400, Got: 201)
✓ PASS - Get All Users (Status: 200)
...

========================================
TEST SUMMARY
========================================

Total Tests: 41
Passed: 39
Failed: 2

🎉 All tests passed! Your API is working correctly.
```

## 🔧 Cenários Testados

### ✅ **Cenários de Sucesso**
- Criação de entidades com dados válidos
- Busca por ID existente
- Atualização com dados válidos
- Relacionamentos válidos entre álbum e figurinha
- Deleção de entidades existentes

### ❌ **Cenários de Erro**
- Busca por ID inexistente (404)
- Atualização de entidade inexistente (404)
- Criação com dados inválidos (400)
- Relacionamentos inválidos (400)
- Deleção de entidade inexistente (404)

## 🎯 **Validações Específicas**

1. **Propriedade de Álbum**: Testa que álbuns são criados com proprietário via path parameter
2. **Imutabilidade**: Testa que update de álbum só modifica o nome
3. **Relacionamentos**: Testa add/remove de figurinhas em álbuns
4. **Foto de Usuário**: Testa endpoint específico para atualizar foto

## 🚨 **Notas Importantes**

- O script cria dados de teste e os limpa ao final
- Cada teste é independente e não afeta os outros
- Status codes são validados conforme esperado
- Respostas de erro são capturadas e exibidas

## 🔄 **Executar Testes Específicos**

Para executar apenas uma seção, você pode comentar as outras seções no script ou criar scripts menores baseados nas funções existentes.

---

**Desenvolvido para testar a API Figurinhas Copa Spring Boot** 🏆
