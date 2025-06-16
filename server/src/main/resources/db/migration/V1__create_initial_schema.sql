-- Таблица сценариев --
CREATE TABLE scenarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    start_descr TEXT,
    mini_description TEXT,
    image_url TEXT,
    difficulty TEXT NOT NULL
);

-- Таблица схем --
CREATE TABLE schemes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    scenario_id INTEGER NOT NULL,
    image_schemes TEXT NOT NULL,
    FOREIGN KEY(scenario_id) REFERENCES scenarios(id)
);

-- Таблица эпиграфов --
CREATE TABLE epigraphs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    quote TEXT NOT NULL,
    author TEXT NOT NULL
);

-- Таблица абзацев с привязкой к эффекту
CREATE TABLE paragraphs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    paragraph_number INTEGER NOT NULL UNIQUE,
    paragraph_descr TEXT NOT NULL,
    effect_id INTEGER,
    FOREIGN KEY (effect_id) REFERENCES effects(id)
);

-- Таблица эффектов
CREATE TABLE effects (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    effect_type TEXT NOT NULL CHECK (effect_type IN ('positive', 'negative', 'choice'))
);

-- Таблица концовок --
CREATE TABLE endings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title_ending TEXT NOT NULL,
    end_descr TEXT NOT NULL,
    alt_question TEXT,
    scenario_id INTEGER NOT NULL,
    FOREIGN KEY (scenario_id) REFERENCES scenarios(id)
);

-- Таблица ресурсов
CREATE TABLE resources (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

-- Таблица комбинаций предметов
CREATE TABLE item_combinations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_resource_id INTEGER NOT NULL,
    second_resource_id INTEGER NOT NULL,
    result_item TEXT NOT NULL,
    image_items TEXT,
    FOREIGN KEY (first_resource_id) REFERENCES resources(id),
    FOREIGN KEY (second_resource_id) REFERENCES resources(id)
);

-- Таблица озвучек к сценариям
CREATE TABLE scenario_audio (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    scenario_id INTEGER NOT NULL,
    audio_url TEXT NOT NULL,
    FOREIGN KEY (scenario_id) REFERENCES scenarios(id)
);


