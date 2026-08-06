-- ============================================================
-- Enable UUID generation (PostgreSQL)
-- ============================================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ============================================================
-- INSERT REGIONS WITH UUID V4
-- ============================================================
INSERT INTO region (id, region_name) VALUES
(gen_random_uuid(), 'Ahafo Region'),
(gen_random_uuid(), 'Ashanti Region'),
(gen_random_uuid(), 'Bono East Region'),
(gen_random_uuid(), 'Bono Region'),
(gen_random_uuid(), 'Central Region'),
(gen_random_uuid(), 'East Region'),
(gen_random_uuid(), 'Greater Accra Region'),
(gen_random_uuid(), 'North East Region'),
(gen_random_uuid(), 'Northern Region'),
(gen_random_uuid(), 'Oti Region'),
(gen_random_uuid(), 'Savannah Region'),
(gen_random_uuid(), 'Upper East Region'),
(gen_random_uuid(), 'Upper West Region'),
(gen_random_uuid(), 'Volta Region'),
(gen_random_uuid(), 'Western North Region'),
(gen_random_uuid(), 'Western Region');


-- ============================================================
-- INSERT DISTRICTS USING REGION UUID REFERENCES
-- ============================================================
INSERT INTO district (id, district_name, region_id)
SELECT
    gen_random_uuid(),
    d.district_name,
    r.id
FROM (
	 VALUES
    ('Adansi Asokwa', 'Ashanti Region'),
    ('Adansi North', 'Ashanti Region'),
    ('Adansi South', 'Ashanti Region'),
    ('Afigya Kwabre North', 'Ashanti Region'),
    ('Afigya Kwabre South', 'Ashanti Region'),
    ('Ahafo Ano North Municipal', 'Ashanti Region'),
    ('Ahafo Ano South East', 'Ashanti Region'),
    ('Ahafo Ano South West', 'Ashanti Region'),
    ('Akrofuom', 'Ashanti Region'),
    ('Amansie Central', 'Ashanti Region'),
    ('Amansie West', 'Ashanti Region'),
    ('Amansie South', 'Ashanti Region'),
    ('Asante Akim Central Municipal', 'Ashanti Region'),
    ('Asante Akim North', 'Ashanti Region'),
    ('Asante Akim South Municipal', 'Ashanti Region'),
    ('Asokore Mampong', 'Ashanti Region'),
    ('Asokwa Municipal', 'Ashanti Region'),
    ('Atwima Kwanwoma', 'Ashanti Region'),
    ('Atwima Mponua', 'Ashanti Region'),
    ('Atwima Nwabiagya Municipal', 'Ashanti Region'),
    ('Atwima Nwabiagya North', 'Ashanti Region'),
    ('Bekwai Municipal', 'Ashanti Region'),
    ('Bosome Freho', 'Ashanti Region'),
    ('Bosomtwe', 'Ashanti Region'),
    ('Ejisu Municipal', 'Ashanti Region'),
    ('Ejura Sekyedumase Municipal', 'Ashanti Region'),
    ('Juaben Municipal', 'Ashanti Region'),
    ('Kumasi Metropolitan', 'Ashanti Region'),
    ('Kwabre East Municipal', 'Ashanti Region'),
    ('Kwadaso Municipal', 'Ashanti Region'),
    ('Mampong Municipal', 'Ashanti Region'),
    ('Obuasi East Municipal', 'Ashanti Region'),
    ('Obuasi Municipal', 'Ashanti Region'),
    ('Offinso Municipal', 'Ashanti Region'),
    ('Offinso North', 'Ashanti Region'),
    ('Oforikrom Municipal', 'Ashanti Region'),
    ('Old Tafo Municipal', 'Ashanti Region'),
    ('Sekyere Afram Plains', 'Ashanti Region'),
    ('Sekyere Central', 'Ashanti Region'),
    ('Sekyere East', 'Ashanti Region'),
    ('Sekyere Kumawu', 'Ashanti Region'),
    ('Sekyere South', 'Ashanti Region'),
    ('Suame Municipal', 'Ashanti Region'),

    ('Banda', 'Bono Region'),
    ('Berekum East', 'Bono Region'),
    ('Berekum West', 'Bono Region'),
    ('Dormaa Central', 'Bono Region'),
    ('Dormaa East', 'Bono Region'),
    ('Dormaa West', 'Bono Region'),
    ('Jaman North', 'Bono Region'),
    ('Jaman South', 'Bono Region'),
    ('Sunyani', 'Bono Region'),
    ('Sunyani West', 'Bono Region'),
    ('Tain', 'Bono Region'),
    ('Wenchi', 'Bono Region'),

    ('Atebubu-Amanten', 'Bono East Region'),
    ('Kintampo North', 'Bono East Region'),
    ('Kintampo South', 'Bono East Region'),
    ('Nkoranza North', 'Bono East Region'),
    ('Nkoranza South', 'Bono East Region'),
    ('Pru East', 'Bono East Region'),
    ('Pru West', 'Bono East Region'),
    ('Sene East', 'Bono East Region'),
    ('Sene West', 'Bono East Region'),
    ('Techiman', 'Bono East Region'),
    ('Techiman North', 'Bono East Region'),

    ('Asunafo North', 'Ahafo Region'),
    ('Asunafo South', 'Ahafo Region'),
    ('Asutifi North', 'Ahafo Region'),
    ('Asutifi South', 'Ahafo Region'),
    ('Tano North', 'Ahafo Region'),
    ('Tano South', 'Ahafo Region'),

    ('Abura Asebu Kwamankese', 'Central Region'),
    ('Agona East', 'Central Region'),
    ('Agona West Municipal', 'Central Region'),
    ('Ajumako Enyan Essiam', 'Central Region'),
    ('Asikuma Odoben Brakwa', 'Central Region'),

    ('Ablekuma Central Municipal', 'Greater Accra Region'),
    ('Ablekuma North Municipal', 'Greater Accra Region'),
    ('Ablekuma West Municipal', 'Greater Accra Region'),
    ('Accra Metropolitan', 'Greater Accra Region'),
    ('Ada East', 'Greater Accra Region'),
    ('Ada West', 'Greater Accra Region'),

    ('Gushegu District', 'Northern Region'),
    ('Karaga District', 'Northern Region'),
    ('Kpandai District', 'Northern Region'),

    ('Bole', 'Savannah Region'),
    ('Central Gonja', 'Savannah Region'),
    ('North Gonja', 'Savannah Region'),

    ('Bawku', 'Upper East Region'),
    ('Bolgatanga', 'Upper East Region'),

    ('Wa Municipal', 'Upper West Region'),
    ('Wa East', 'Upper West Region'),
    ('Wa West', 'Upper West Region'),

    ('Ho', 'Volta Region'),
    ('Hohoe', 'Volta Region'),
    ('Keta', 'Volta Region'),

    ('Jasikan', 'Oti Region'),
    ('Kadjebi', 'Oti Region'),

    ('Sekondi-Takoradi', 'Western Region'),
    ('Shama', 'Western Region'),

    ('Sefwi-Wiawso', 'Western North Region'),
    ('Bibiani/Anhwiaso/Bekwai', 'Western North Region')

) AS d(district_name, region_name)
JOIN region r
    ON r.region_name = d.region_name;