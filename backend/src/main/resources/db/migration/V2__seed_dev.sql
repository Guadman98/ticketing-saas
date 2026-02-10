-- DEV seed (fixed IDs)
INSERT INTO organizations (id, name)
VALUES ('11111111-1111-1111-1111-111111111111', 'Dev Org')
ON CONFLICT DO NOTHING;

INSERT INTO users (id, org_id, email, password_hash, role, full_name)
VALUES (
  '22222222-2222-2222-2222-222222222222',
  '11111111-1111-1111-1111-111111111111',
  'admin@dev.local',
  '{noop}dev', -- dummy, luego lo cambiaremos por BCrypt
  'ADMIN',
  'Dev Admin'
)
ON CONFLICT DO NOTHING;