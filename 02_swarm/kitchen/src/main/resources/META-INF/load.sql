insert into recipe (id, title, description) values (1, 'Brioche', 'Make the dough, let it rise, rise more, shape, proof, bake, eat!');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 400, 'G', 'Flour', 'SKU98575');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 0.5, 'TSP', 'Yeast', 'SKU01002');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 2, 'TSP', 'Sugar', 'SKU01032');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 1, 'DL', 'Milk', 'SKU01042');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 1, 'TSP', 'Salt', 'SKU01052');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 150,  'G', 'Butter', 'SKU01062');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (1, 2, 'PIECE', 'Eggs', 'SKU32987');

insert into recipe (id, title, description) values (2, 'Tarragon Peas', 'Heat the chicken stock, reduce it to half. Add the butter, salt it. Add peas and tarragon and heat softly before serving');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (2, 5, 'DL', 'Chicken Stock', 'SKU86575');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (2, 75, 'G', 'Butter', 'SKU01062');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (2, 300, 'G', 'Peas, fresh or frozen', 'SKU45825');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (2, 15, 'G', 'Tarragon', 'SKU28943');

insert into recipe (id, title, description) values (3, 'Chicken Supremes', 'Heat oil in pan at high temperature. Season chicken, add it to pan when hot. Sear 2-3 minutes. Add thyme and butter, cover chicken with butter. Put in the oven for 12-15 minutes until done.');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (3, 2, 'TSP', 'Olive oil', 'SKU55712');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (3, 1, 'PIECE', 'Chicken breast', 'SKU94773');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (3, 10, 'G', 'Thyme', 'SKU78923');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (3, 100, 'G', 'Butter', 'SKU01062');

insert into recipe (id, title, description) values (4, 'Pavlova', 'Beat the egg whites, sugar, salt and lemon juice over hot water until it reaches 70 deg. Remove and beat firm. Add more sugar, pile and bake for an hour at 120 deg.');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (4, 4, 'PIECE', 'Eggs', 'SKU32987');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (4, 180, 'G', 'Sugar', 'SKU01032');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (4, 1, 'G', 'Salt', 'SKU01052');
insert into recipe_ingredients (recipe_id, measure, unit, name, stockid) values (4, 1, 'PIECE', 'Lemon', 'SKU11954');

insert into menu (id, name, description) values (1, 'Brioche Surprise', 'Brioche with surprise content');
insert into menu_recipe (menu_id, recipes_id) values (1, 1);

insert into menu (id, name, description) values (2, 'Tarragon Chicken', 'Chicken supremes with tarragon peas and brioche');
insert into menu_recipe (menu_id, recipes_id) values (2, 1);
insert into menu_recipe (menu_id, recipes_id) values (2, 2);
insert into menu_recipe (menu_id, recipes_id) values (2, 3);

insert into menu (id, name, description) values (3, 'Pavlova', 'Very light dessert.');
insert into menu_recipe (menu_id, recipes_id) values (3, 4);

ALTER SEQUENCE hibernate_sequence RESTART WITH 100;
