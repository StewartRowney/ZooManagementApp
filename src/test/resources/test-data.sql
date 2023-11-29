INSERT INTO ZOO (Id, Name, Location, Capacity, Date_Opened, Price)
VALUES
('40ea5519-fcef-4272-b742-e01790ca04c3', 'Edinburgh Zoo', 'Edinburgh', 300, '2015-10-17', 10.0),
('7cf4649d-32a6-40a8-9160-92073bf64b13', 'Manchester Zoo', 'Manchester', 125, '1990-10-20', 15.0),
('3931d736-38bc-4cf6-ae01-116d5969e65e', 'The best Zoo', 'Somewhere in India', 12000, '1947-03-27', 25.0),
('3931d736-38bc-4cf6-ae01-116d5969e69e', 'The best Zoo', 'Somewhere in India', 12000, '1947-03-27', 25.0);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Has_Fins, Has_Fur, Has_Hooves)
VALUES
('7a12afea-8b9b-4a7f-94f2-5b57f4e7ffa7','3931d736-38bc-4cf6-ae01-116d5969e65e', 'Jimbo', 'Elephant', '2007-04-18',
'Jungle', 'Docile', 'Leaves, Fruit, Veg', 'Has pure mega feet', 'Mammal', false, false, false),
('2479f54f-b8c4-449f-a54c-31fd1d6074dc','40ea5519-fcef-4272-b742-e01790ca04c3', 'Ayush', 'Gorilla', '2015-10-12',
'Jungle', 'Aggressive', 'Fruit', 'Has small tail', 'Mammal', false, true, false);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Has_Legs, Has_Shell, Is_Cold_Blooded)
VALUES
('9b2d9232-9385-4707-965f-e5a90cbcfc88','3931d736-38bc-4cf6-ae01-116d5969e65e','Sally', 'Python', '2020-10-12',
'Jungle', 'Docile', 'Meat', 'Only eats twice a month', 'Reptile', false, false, true),
('b5f36d59-d08f-4165-958c-7e84132cc026','40ea5519-fcef-4272-b742-e01790ca04c3','Greg', 'Gecko', '2017-03-01',
'Jungle', 'Docile', 'Insects', 'They sell insurance', 'Reptile', true, false, true);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Can_Fly, Can_Mimic_Sound, Is_Nocturnal)
VALUES
('952a60c2-e4ad-422c-bb55-7f1aad97c15d', '7cf4649d-32a6-40a8-9160-92073bf64b13', 'Stew', 'Cockatoo', '2013-09-05', 'Scrublands', 'Erratic',
'Seeds', 'Good at coding', 'Bird', true, true, false),
('ab8507f2-daa1-4549-8a32-70acee9b158a', '3931d736-38bc-4cf6-ae01-116d5969e65e', 'Oscar', 'Penguin', '2008-09-09', 'Ice flats', 'Strange',
'Fish', 'They are a good tasting chocolate bar','Bird', false, false, false);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Can_Discharge_Electricity, Is_Bioluminiscent)
VALUES
('8bb33239-24df-473e-9d35-2ca6a9bdd9ca', '40ea5519-fcef-4272-b742-e01790ca04c3', 'Diane', 'Hammerhead Shark', '2022-08-19',
'Sea', 'Hunter', 'Meat', 'They can not hold a hammer', 'Fish', false, false),
('b275712e-e0c6-45ed-8a1d-e38fd0753eb9', '3931d736-38bc-4cf6-ae01-116d5969e65e', 'Abhijeet', 'Angler Fish', '2015-06-06',
'Deep-Sea', 'Just chills out', 'Debris', 'They are bad at geometry', 'Fish', false, true);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Has_Wings, Number_Of_Legs)
VALUES
('8a3cdf04-19b5-4acc-90d7-8ad8abef523e', '40ea5519-fcef-4272-b742-e01790ca04c3', 'Sid', 'Black Widow Spider', '2023-07-10',
'Trees', 'Scary', 'Flies', 'Scared of Spiders', 'Insect', false, 8),
('26ba3474-846b-4a6d-8f43-6df6f981dbe6', '40ea5519-fcef-4272-b742-e01790ca04c3', 'Terry', 'Worm', '2023-10-10', 'Soil',
'Slimy', 'Honestly no idea', 'Pretty wormy', 'Insect', false, 0);

INSERT INTO Animal (Id, Zoo_Id, Name, Species_Name, Birth_Date, Habitat, Behaviour, Food_Type, Extra_Information, DType,
Is_Poisonous, Makes_Noise)
VALUES
('60229efa-5978-4cf0-b2f8-76a690ef32b6', '40ea5519-fcef-4272-b742-e01790ca04c3', 'Jerry', 'Cane Toad', '2000-12-09', 'Pond',
'Great guy', 'Flies', 'Loves a pint', 'Amphibian', true, true),
('ae0a05f4-7255-47fb-b565-3fbf20c6464f', '7cf4649d-32a6-40a8-9160-92073bf64b13', 'Lucy', 'Giant African Snail', '1900-10-15',
 'The streets','Stone cold killer', 'People', 'Is on the FBIs most wanted list', 'Amphibian', false, false);