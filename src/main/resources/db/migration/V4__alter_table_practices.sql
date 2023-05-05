ALTER TABLE practices
    RENAME COLUMN user_id TO owner_name;

ALTER TABLE practices
    DROP CONSTRAINT practices_user_id_fkey;

BEGIN;

-- создаем временный столбец с разрешением на NULL-значения
ALTER TABLE practices ADD COLUMN temp_owner_name VARCHAR(255);

-- заполняем временный столбец значениями из столбца owner_name
UPDATE practices SET temp_owner_name = owner_name;

-- удаляем столбец owner_name
ALTER TABLE practices DROP COLUMN owner_name;

-- переименовываем временный столбец
ALTER TABLE practices RENAME COLUMN temp_owner_name TO owner_name;

COMMIT;