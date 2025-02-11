package grcmcs.minecraft.mods.pomkotsmechs.extension;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import grcmcs.minecraft.mods.pomkotsmechs.client.renderer.GrenadeLargeEntityRenderer;
import grcmcs.minecraft.mods.pomkotsmechs.client.renderer.MissileBaseEntityRenderer;
import grcmcs.minecraft.mods.pomkotsmechs.extension.client.renderer.*;

public class PomkotsMechsExtensionClient {
	public static void initialize() {
		// Mobile Suites
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGZ01, (context)->{
			return new Pmgz01EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGZ02, (context)->{
			return new Pmgz02EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGZ03, (context)->{
			return new Pmgz03EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGZ03C, (context)->{
			return new Pmgz03yEntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.PMGE01, (context)->{
			return new Pmge01EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGE02, (context)->{
			return new Pmge02EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMGE03, (context)->{
			return new Pmge03EntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.PMGX01, (context)->{
			return new Pmgx01EntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.PMAC01, (context)->{
			return new Pmac01EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMAC01C, (context)->{
			return new Pmac01cEntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.PMAC02, (context)->{
			return new Pmac02EntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.PMAC02C, (context)->{
			return new Pmac02cEntityRenderer(context);
		});

		// Projectiles
		EntityRendererRegistry.register(PomkotsMechsExtension.BULLETLARGE, (context)->{
			return new BulletLargeEntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.BEAM, (context)->{
			return new BeamEntityRenderer(context);
		});
		EntityRendererRegistry.register(PomkotsMechsExtension.BEAMLARGE, (context)->{
			return new BeamLargeEntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.MISSILE, (context)->{
			return new MissileBaseEntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.MACHINEGUNBULLET, (context)->{
			return new MachineGunBulletEntityRenderer(context);
		});

		EntityRendererRegistry.register(PomkotsMechsExtension.ZAKUBAZ, (context)->{
			return new GrenadeLargeEntityRenderer(context);
		});
	}
}