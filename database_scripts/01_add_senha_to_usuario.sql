-- Run this script in your Supabase SQL Editor to add the 'senha' column
-- It adds the column to the existing 'usuarios' table (or the generated one by JPA, usually 'usuario_jpa_entity' or 'usuarios')

ALTER TABLE usuario_jpa_entity ADD COLUMN senha VARCHAR(255);

-- Se a sua tabela se chama apenas "usuarios" ou "usuario", descomente a linha abaixo e use ela:
-- ALTER TABLE usuario ADD COLUMN senha VARCHAR(255);
